create table Address(address_Id int not null, first_Line varchar(100) ,second_line varchar(100), City varchar(50),
zip_Code varchar(15) ,primary key (address_Id));


Create table Customer( customer_Id int not null, 	
	first_Name varchar(50),	
	Last_Name varchar(50),	
	Address_Id int,	
	primary key (customer_Id),	
	FOREIGN KEY (Address_Id) REFERENCES Address(Address_Id));