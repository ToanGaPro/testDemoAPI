package net.codejava.ws;

import java.util.List;

public interface UserInterface<T> {
	public List<T> getAllUser();

	public T getUserById(int id);

	public void addUser(T t);

	public void updateUser(T t);

	public void deleteUser(int id);

}