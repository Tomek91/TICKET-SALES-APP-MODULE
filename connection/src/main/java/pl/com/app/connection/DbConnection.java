package pl.com.app.connection;

import pl.com.app.exceptions.MyException;
import pl.com.app.sqlbuilder.creator.SqlTableCreator;
import pl.com.app.sqlbuilder.types.Types;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnection {
    private static DbConnection ourInstance = new DbConnection();
    public static DbConnection getInstance() {
        return ourInstance;
    }

    private final static String DRIVER = "org.sqlite.JDBC";
    private final static String DB_URL = "jdbc:sqlite:Sales.db";

    private Connection connection;

    private DbConnection() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DB_URL);
            createTables();
        } catch (Exception e) {
            throw new MyException("CONNECTION ERROR");
        }
    }

    public static Connection connection() {
        return DbConnection.getInstance().getConnection();
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            throw new MyException("CLOSE CONNECTION ERROR");
        }
    }

    private void createTables() throws SQLException {

        Statement statement = connection.createStatement();

        final String sqlMovie = new SqlTableCreator
                .SqlTableCreatorBuilder()
                .tableName("movie")
                .addPrimaryKey("id")
                .addColumn("title", Types.VARCHAR).maxSize(50).isNotNull()
                .addColumn("genre", Types.VARCHAR).maxSize(50).isNotNull()
                .addColumn("price", Types.DOUBLE).isNotNull()
                .addColumn("duration", Types.INTEGER).isNotNull()
                .addColumn("release_date", Types.DATE).isNotNull()
                .build();

        statement.execute(sqlMovie);

        final String sqlLoyaltyCard = new SqlTableCreator
                .SqlTableCreatorBuilder()
                .tableName("loyalty_card")
                .addPrimaryKey("id")
                .addColumn("expiration_date", Types.DATE).isNotNull()
                .addColumn("discount", Types.DOUBLE).isNotNull()
                .addColumn("movies_number", Types.INTEGER).isNotNull()
                .build();

        statement.execute(sqlLoyaltyCard);

        final String sqlCustomer= new SqlTableCreator
                .SqlTableCreatorBuilder()
                .tableName("customer")
                .addPrimaryKey("id")
                .addColumn("name", Types.VARCHAR).maxSize(50).isNotNull()
                .addColumn("surname", Types.VARCHAR).maxSize(50).isNotNull()
                .addColumn("age", Types.INTEGER).isNotNull()
                .addColumn("email", Types.VARCHAR).maxSize(50).isNotNull()
                .addForeignKey("loyalty_card_id", Types.INTEGER, "loyalty_card", "id")
                .build();

        statement.execute(sqlCustomer);

        final String sqlSalesStand = new SqlTableCreator
                .SqlTableCreatorBuilder()
                .tableName("sales_stand")
                .addPrimaryKey("id")
                .addForeignKey("customer_id", Types.INTEGER, "customer", "id").isNotNull()
                .addForeignKey("movie_id", Types.INTEGER, "movie", "id").isNotNull()
                .addColumn("start_date_time", Types.TIMESTAMP).isNotNull()
                .build();

        statement.execute(sqlSalesStand);
    }
}
