ALTER TABLE users
    ADD activation_code varchar(255);
ALTER TABLE users
    ADD enabled boolean default false;