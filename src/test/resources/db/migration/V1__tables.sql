create table "user" (
  "id" SERIAL PRIMARY KEY not null,
  "name" varchar not null
);

create table "wallet" (
  "id" SERIAL PRIMARY KEY not null,
  "user_id" int not null,
  "amount" double not null,
  foreign key ("user_id") references "user"("id") on delete cascade
);

create table "transfer" (
  "id" SERIAL PRIMARY KEY not null,
  "sender_id" int not null,
  "receiver_id" int not null,
  "amount" double not null,
  foreign key ("sender_id") references "user"("id") on delete cascade,
  foreign key ("receiver_id") references "user"("id") on delete cascade
);