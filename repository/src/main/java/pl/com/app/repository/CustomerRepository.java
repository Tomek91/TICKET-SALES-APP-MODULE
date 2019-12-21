package pl.com.app.repository;

import pl.com.app.model.Customer;
import pl.com.app.sqlbuilder.creator.SqlInsertCreator;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    Optional<Customer> findOneByLoyaltyCardId(Integer loaltyCardId);
    List<Customer> findByCustom(String columnName, Object value);
    Optional<Customer> findByNameSurnameAndEmail(String name, String surname, String email);
    void updateAll(String columnName, Object columnValue);

    static String getInsertSql(Customer item){
        return new SqlInsertCreator
                .SqlInsertBuilder()
                .tableName("customer")
                .addCondition("name", item.getName())
                .addCondition("surname", item.getSurname())
                .addCondition("age", item.getAge())
                .addCondition("email", item.getEmail())
                .addCondition("loyalty_card_id", item.getLoyaltyCardId())
                .build();
    }
}
