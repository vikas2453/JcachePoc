create table Address(
addressId int not null, 
varchar(100) firstLine, 
	varchar(100) secondline;
	varchar(50) City;
	varchar(15) zipCode;
	primary key (addressId),
);

insert into Address(1, '1105 fort clarke', 'Gainesville, '32606');

Create table customer( 
	customerId int not null, 
	
	varchar(50) firstName;
	
	varchar(50) LastName, 
	
	AddressId int,
	
	primary key (customerId),
	
	FOREIGN KEY (AddressId) REFERENCES Address(AddressId));