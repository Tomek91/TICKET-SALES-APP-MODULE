package pl.com.app.repository;

import pl.com.app.connection.DbConnection;
import pl.com.app.exceptions.MyException;
import pl.com.app.sqlbuilder.creator.SqlDeleteCreator;

import java.sql.Connection;
import java.sql.Statement;

public class SequenceRepositoryImpl implements SequenceRepository {

    Connection connection = DbConnection.getInstance().getConnection();

    @Override
    public void deleteAll() {
        try {
            final String deleteAllSql = new SqlDeleteCreator
                    .SqlDeleteBuilder()
                    .tableName("sqlite_sequence")
                    .build();

            Statement statement = connection.createStatement();
            statement.execute(deleteAllSql);
        } catch (Exception e) {
            throw new MyException("SQLITE SEQUENCE REPOSITORY, DELETE ALL");
        }
    }
}
