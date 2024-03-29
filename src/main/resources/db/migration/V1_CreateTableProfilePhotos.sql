CREATE TABLE IF NOT EXISTS profile_photos (
  customer_id VARCHAR(36) not null,
  id VARCHAR(36) not null,
  original_photo VARCHAR(200),
  generated_photo VARCHAR(200),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (customer_id, id)
);

INSERT INTO profile_photos (customer_id, id, original_photo, generated_photo) values 
  ("customer-1", "06043448-f8be-lled-be56-0242ac120002", "customer-1-original-photo-1-path", "customer-1-generated-photo-1-path"),
  ("customer-1", "0c146f8e-f8be-lled-be56-0242ac120002", "customer-1-original-photo-2-path", "customer-1-generated-photo-2-path"),
  ("customer-2", "0fba6f8e-f8be-lled-be56-0242ac120002", "customer-2-original-photo-1-path", "customer-1-generated-photo-1-path");