package pl.com.app.service.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.com.app.exceptions.MyException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Menu {
    private List<MenuItem> menuList;

    public static String showMenu(List<MenuItem> menu){
        if (menu == null){
            throw new MyException("MENU IS NULL");
        }

        return menu
                .stream()
                .map(x -> String.join(" - ", x.getCode(), x.getName()))
                .collect(Collectors.joining("\n"));
    }

    public static List<MenuItem> createMenu() {
        return Arrays.asList(
                MenuItem.builder().code("1").name("dodaj klienta").build(),
                MenuItem.builder().code("2").name("dodaj nowy film do repertuaru kina").build(),
                MenuItem.builder().code("3").name("załaduj domyślne dane do bazy danych").build(),
                MenuItem.builder().code("4").name("zarządzaj klientami").build(),
                MenuItem.builder().code("5").name("zarządzaj filmami").build(),
                MenuItem.builder().code("6").name("zarządzaj kontem").build(),
                MenuItem.builder().code("7").name("statystyki").build(),
                MenuItem.builder().code("q").name("koniec aplikacji").build()
        );
    }

    public static List<MenuItem> createStatisticsMenu() {
        return Arrays.asList(
                MenuItem.builder().code("a").name("Klienci, którzy zakupili co najmniej trzy bilety na film z gatunku HORROR").build(),
                MenuItem.builder().code("b").name("Nazwa filmu, na którym zarobiono najwięcej.").build(),
                MenuItem.builder().code("c").name("Klienci, którym wygasa karta po dacie podanej jako argument.").build(),
                MenuItem.builder().code("d").name("Najnowszy i najstarszy film, który obejrzało już przynajmniej 4 osoby.").build(),
                MenuItem.builder().code("e").name("Łączny zysk kina, z uwzględnieniem zniżek.").build(),
                MenuItem.builder().code("f").name("Wykaz klientów, którzy kupili bilet na dany film.").build(),
                MenuItem.builder().code("q").name("wyjdź").build()
        );
    }

    public static List<MenuItem> createManageMenu() {
        return Arrays.asList(
                MenuItem.builder().code("do").name("Usuń pojedyńczy rekord.").build(),
                MenuItem.builder().code("da").name("Usuń wszystkie rekordy.").build(),
                MenuItem.builder().code("uo").name("Modyfikuj pojedyczńczy rekord.").build(),
                MenuItem.builder().code("ua").name("Modyfikuj wszystkie rekordy.").build(),
                MenuItem.builder().code("fo").name("Podgląd pojedyczńczego rekordu.").build(),
                MenuItem.builder().code("fa").name("Podgląd wszystkich rekordów.").build(),
                MenuItem.builder().code("q").name("wyjdź").build()
        );
    }

    public static List<MenuItem> createAccountMenu() {
        return Arrays.asList(
                MenuItem.builder().code("k").name("Kup bilet.").build(),
                MenuItem.builder().code("h").name("Historia transakcji.").build(),
                MenuItem.builder().code("q").name("wyjdź").build()
        );
    }

    public static List<MenuItem> createFilterMenu() {
        return Arrays.asList(
                MenuItem.builder().code("w").name("gatunek filmu").build(),
                MenuItem.builder().code("x").name("zakres dat").build(),
                MenuItem.builder().code("y").name("długość trwania seansu").build(),
                MenuItem.builder().code("z").name("gatunek filmu, zakres dat, długość trwania seansu jednocześnie").build(),
                MenuItem.builder().code("q").name("wyjdź").build()
        );
    }

}