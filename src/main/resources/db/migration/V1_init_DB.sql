create sequence global_seq start with 100000 increment by 1;

create table if not exists users
(
    id        bigint primary key default nextval('global_seq'),
    archive   boolean not null,
    bucket_id bigint unique,
    email     varchar(255),
    name      varchar(255),
    password  varchar(255),
    roles     varchar(255) check (roles in ('USER', 'GUEST', 'ADMIN', 'MANAGER','CLIENT'))
);

create table if not exists products
(
    id    bigint primary key default nextval('global_seq'),
    price numeric(38, 2),
    title varchar(255)
);

create table if not exists buckets
(
    id      bigint primary key default nextval('global_seq'),
    user_id bigint unique
);

create table if not exists categories
(
    id    bigint primary key default nextval('global_seq'),
    title varchar(255)
);

create table if not exists orders
(
    id      bigint primary key default nextval('global_seq'),
    created timestamp,
    updated timestamp,
    sum     numeric(38, 2),
    user_id bigint,
    address varchar(255),
    status  varchar(255) check (status in ('NEW', 'APPROVED', 'CANCEL', 'PAID', 'CLOSED'))
);

create table if not exists orders_details
(
    id         bigint primary key default nextval('global_seq'),
    amount     numeric(38, 2),
    price      numeric(38, 2),
    details_id bigint not null unique,
    order_id   bigint,
    product_id bigint
);

create table if not exists products_categories
(
    category_id bigint not null,
    product_id  bigint not null
);

create table if not exists buckets_products
(
    bucket_id  bigint not null,
    product_id bigint not null

);

alter table if exists buckets_products
    add constraint products_fk_backers foreign key (product_id) references products;
alter table if exists buckets_products
    add constraint backers_fk_products foreign key (bucket_id) references buckets;
alter table if exists buckets
    add constraint backers_fk_users foreign key (user_id) references users;
alter table if exists orders
    add constraint orders_fk_users foreign key (user_id) references users;
alter table if exists orders_details
    add constraint order_details_fk_order foreign key (order_id) references orders;
alter table if exists orders_details
    add constraint orders_details_fk_products foreign key (product_id) references products;
alter table if exists orders_details
    add constraint orders_details_fk_details foreign key (details_id) references orders_details;
alter table if exists products_categories
    add constraint products_fk_categories foreign key (category_id) references categories;
alter table if exists products_categories
    add constraint categories_fk_products foreign key (product_id) references products;
alter table if exists users
    add constraint users_fk_buckets foreign key (bucket_id) references buckets;