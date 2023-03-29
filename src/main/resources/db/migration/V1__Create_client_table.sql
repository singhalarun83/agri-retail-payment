
CREATE TABLE client(
    id bigint not null AUTO_INCREMENT,
    client_id varchar(30) NOT NULL,
    client_name varchar(50) NOT NULL,
    description varchar(250),
    created_on datetime NOT NULL,
    updated_on datetime,
    created_by VARCHAR(30) NOT NULL,
    updated_by VARCHAR(30),
    PRIMARY KEY(id)
);