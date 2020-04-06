
 CREATE TABLE language_component 
 (
   language_component_id INT NOT NULL,
   language_component_name VARCHAR(50) NOT NULL,
   sequence_no INT NOT NULL,
   PRIMARY KEY (language_component_id) 
);

 CREATE TABLE word (
  word_id INT NOT NULL,
  word_name varchar(255) DEFAULT NULL,
  language_component_id INT DEFAULT NULL,
  PRIMARY KEY (word_id)
);
