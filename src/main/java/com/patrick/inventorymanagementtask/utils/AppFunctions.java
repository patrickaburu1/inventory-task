package com.patrick.inventorymanagementtask.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author patrick on 3/23/20
 * @project  inventory
 */
@Service
public class AppFunctions {

    private static Logger logger = LoggerFactory.getLogger("AppFunctions");

    /**
     * generate random  code
     */
    public String randomCodeNumber(int length) {
        Random random = new Random();
        char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return new String(digits);
    }


    /**
     * validate phone number
     *
     * @return boolean
     */
    public boolean validatePhoneNumber(String phone) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber verifyPhonenumber;
        try {
            verifyPhonenumber = phoneUtil.parse(phone, AppConstants.KENYA_COUNTRY_CODE);
        } catch (NumberParseException e) {
            logger.info("NumberParseException was thrown: " + e.toString());
            return false;
        }
        return phoneUtil.isValidNumber(verifyPhonenumber);
    }


    public String getInternationalPhoneNumber(String mobileNumber, String country) {
        try {
            if (null == country || country.isEmpty() || " ".equals(country)) {
                country = AppConstants.KENYA_COUNTRY;
            }
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber numberProto = null;

            //when the country is defined
            if (null != country || " ".equals(country) || country.isEmpty()) {
                Map<String, String> countries = new HashMap<>();
                for (String isoCountry : Locale.getISOCountries()) {
                    Locale l = new Locale("", isoCountry);
                    countries.put(l.getDisplayCountry(), isoCountry);
                }
                String countryCode = countries.get(country);
                numberProto = phoneUtil.parse(mobileNumber, countryCode);
            }
            //when country is unknown
            else {
                numberProto = phoneUtil.parse(mobileNumber, "");
            }

            return phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.E164);
        } catch (NumberParseException e) {
            logger.error("*********** Error parsing phone number *************", e);
            return mobileNumber;
        } catch (Exception e) {
//            appAuditLogRepo.mailError(e.getLocalizedMessage());
            logger.error("*********** Error getInternationalPhoneNumber() *************", e);
            return mobileNumber;
        }
    }


    public String formatDateTime(Date date) {
        SimpleDateFormat sF = new SimpleDateFormat("dd MMMM yyyy HH:mm");
        String df = sF.format(date);
        return df;
    }

    public String formatStrDateToSystemDate(String strdate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(strdate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return formatter.format(date);
    }

    public Date formatStringToDate(String strdate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(strdate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return date;
    }

    public String formatDateToStringDateSystemFormat(Date date) {
        SimpleDateFormat dateFormter = new SimpleDateFormat("yyyy-MM-dd");

        return dateFormter.format(date);
    }


    public String formatDateToDateWithMonthName(String strdate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(strdate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;


        }
        SimpleDateFormat dateFormter = new SimpleDateFormat("dd MMM yyyy");

        return dateFormter.format(date);
    }

    public String formatToHumandateTime(Date date) {
        SimpleDateFormat dateFormter = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        return dateFormter.format(date);
    }

    public String formatToHumanDateTimeAmPm(Date date) {
        SimpleDateFormat dateFormter = new SimpleDateFormat("dd-MM-yyyy hh:mm a");

        return dateFormter.format(date);
    }
    public Integer getDiffBetweenDates(String from, String to) {

        long daysBetween=0;
        try {
            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateTo = myFormat.parse(to);
            Date dateFrom = myFormat.parse(from);

            long difference = dateFrom.getTime() - dateTo.getTime();
            daysBetween = (difference / (1000*60*60*24));

            System.out.println("Number of Days between dates: "+daysBetween);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("================ an error occurred while trying to get date difference ");
        }

        return Math.toIntExact((daysBetween));
    }

    public Long getDiffBetweenDatesLong(String from, String to) {
        long daysBetween=0;
        try {
            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateTo = myFormat.parse(to);
            Date dateFrom = myFormat.parse(from);

            long difference = dateFrom.getTime() - dateTo.getTime();
            daysBetween = (difference / (1000*60*60*24));

            System.out.println("Number of Days between dates: "+daysBetween);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("================ an error occurred while trying to get date difference ");
        }
        return daysBetween;
    }
}
