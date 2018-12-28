package com.kizzington.server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class UserFile implements Serializable {
	private static final long serialVersionUID = 1L;

	public String username, password;
	
	public void saveUser() throws IOException {
		FileOutputStream fileOut = new FileOutputStream("data/users/" + username);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(this);
        objectOut.close();
	}
}
