CREATE TABLE Tweets (
	id bigint primary key,
    userScreenName varchar(512) null,
    userLocation varchar(1024) null,
    statusText mediumtext null,
    latitude double null,
    longitude double null,
	createdDate datetime,
    updatedDate datetime 
);


CREATE TABLE HashTags (
	id int auto_increment primary key,
    tweetId bigint not null references Tweets(id),
    hashTag varchar(512) not null
);