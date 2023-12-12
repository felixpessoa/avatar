create table if not exists profile_photos (
    customer_id varchar(36) not null,
    id varchar(36) not null,
    original_photo varchar(200),
    generated_photo varchar(200),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    primary key (customer_id, id)
)

insert into profile_photos (customer_id, id, original_photo, generated_photo) values 
("customer_1", "06043448-f8be-lled-be56-0242ac120002", "customer-1-original-photo-1-path", "customer-1-generated-photo-1-path"),
("customer_1", "0c146f8e-f8be-lled-be56-0242ac120002", "customer-1-original-photo-2-path", "customer-1-generated-photo-2-path"),
("customer_2", "06043448-f8be-lled-be56-0242ac120002", "customer-2-original-photo-1-path", "customer-1-generated-photo-1-path");
