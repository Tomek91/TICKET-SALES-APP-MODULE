package pl.com.app.repository;


import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {
    void add(T item);
    void addAll(List<T> items);
    void update(T item);
    void delete(ID id);
    void deleteAll();
    Optional<T> findOneById(ID id);
    List<T> findAll();
}
