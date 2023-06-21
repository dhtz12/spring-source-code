package org.springframework.applicationContext;

import org.springframework.stereotype.Component;

@Component
public class User {
	/**
	 * name.
	 */
	private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
