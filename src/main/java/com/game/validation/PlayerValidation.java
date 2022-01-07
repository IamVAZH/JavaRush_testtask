package com.game.validation;

import com.game.entity.Player;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.zip.DataFormatException;

//Класс для проверки персонажа на корректность данных
public class PlayerValidation {

    //Проверка на возможность создание персонажа
    public static boolean canCreate(Player player) {
        return !(player.getName() == null ||
                player.getTitle() == null ||
                player.getRace() == null ||
                player.getProfession() == null ||
                player.getBirthday() == null ||
                player.getExperience() == null);
    }

    //Проверка всех данных
    public static boolean isValidData(Player player) {
        return checkName(player.getName()) &&
                checkTitle(player.getTitle()) &&
                checkBirthday(player.getBirthday()) &&
                checkExperience(player.getExperience());
    }

    //Проверка поля name
    public static boolean checkName(String name) {
        return name.length() < 12 && !name.equals("");
    }

    //Проверка поля title
    public static boolean checkTitle(String title) {
        return title.length() < 30;
    }

    //Проверка поля experience
    public static boolean checkExperience(Integer exp) {
        return exp == null || (exp >=0 && exp <= 10000000);
    }

    //Проврека поля birthday
    public static boolean checkBirthday(Date date) {
        if (date == null) {
            return true;
        }
        Date startDate = new GregorianCalendar(2000, Calendar.JANUARY,0).getTime();
        Date endDate = new GregorianCalendar(3000, Calendar.JANUARY,0).getTime();
        return date.getTime() > 0 && date.after(startDate) && date.before(endDate);
    }

}
