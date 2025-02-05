CREATE TABLE Post (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(200) NOT NULL,
  content TEXT NOT NULL,
  createdAt timestamp NOT NULL,
  author VARCHAR(200),
  category VARCHAR(100),
  updatedAt timestamp,
  likesCount INT NOT NULL DEFAULT 0,
  authorId INT,
  isPublished BOOLEAN NOT NULL,
  views INT NOT NULL DEFAULT 0
);
