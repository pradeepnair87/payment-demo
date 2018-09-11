package com.pradeep.repository;

public interface BaseRepository<T> {

	public void insert(T t);

	public T fetch(Long id);

	public T delete(Long id);

	public T fetch(String name);

	public T delete(String name);
}
