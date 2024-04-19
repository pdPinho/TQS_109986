CREATE TABLE car(
  id BIGSERIAL PRIMARY KEY,
  maker varchar(60),
  model varchar(60)
);


INSERT INTO car (maker, model) VALUES
("ferrari", "f50");