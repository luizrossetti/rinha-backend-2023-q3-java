create table pessoa (nascimento date not null, id uuid not null, apelido varchar(32) not null, nome varchar(100), primary key (id)) engine=InnoDB;
create table stack (id uuid not null, stack varchar(255) not null) engine=InnoDB;
alter table if exists pessoa add constraint UK_mupg31uvc3g3c6gw4ko94oby9 unique (apelido);
alter table if exists stack add constraint FKto5lt0adgbc6smlmg4bhy6chk foreign key (id) references pessoa (id);