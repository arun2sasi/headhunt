CREATE TABLE twitter_users (
  id           INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name         TEXT,
  url          TEXT,
  link         TEXT,
  location     TEXT,
  bio          TEXT,
  created      DATETIME,
  account_type TEXT,
  picture_url  TEXT
);
