package com.qiheng.hibernate;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class CreateTable {
	public static void main(String[] args) {
		
		SchemaExport schemaExport = new SchemaExport(new Configuration().configure());
		schemaExport.create(true, true);
		
		
	}
}
