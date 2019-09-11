package ua.hillel.java.elementary1.objects.impl.kosenkov;

import ua.hillel.java.elementary1.objects.tasks.Languaged;
import ua.hillel.java.elementary1.objects.tasks.Named;
import ua.hillel.java.elementary1.objects.tasks.TimeOffset;

//  Create enumeration of the countries. Add possibility to return language mostly used in such country.
//  You must implement interface Languaged in current project.
//  Extend you enumuration and add possibility to get name of the country
//  and create country instance by provided name. You code should throw IllegalArgumentException
//  in case name provided in method is null or country not found.
//  Extend you enumuration and add possibility to get default time-offset
//  (make avg in case country has few timezones like US) from UTC. Implement TimeOffset.
//  Create and throw unchecked exception when timezone provided is not correct.

enum Countries implements Languaged, Named, TimeOffset {

    UA("Ukraine", "Ukrainian", 2),
    LT("Lithuania", "Lithuanian", 2),
    PL("Poland", "Polish", 1),
    RU("Russia", "Russian", 3),
    GE("Germany", "German", 1),
    FR("France", "French", 1),
    UK("UK", "English", 0),
    ES("Spain", "Spanish", 1),
    PT("Portugal", "Portuguese", 0),
    MX("Mexico", "Spanish", -6),
    BR("Brazil", "Portuguese", -3),
    AR("Argentina", "Spanish", -3),
    US("USA", "English", -7),
    IL("Israel", "Hebrew", 2);

    private final String name;
    private final String language;
    private final int utcOffset;

    public static class IllegalTimeOffsetException extends RuntimeException{
        public IllegalTimeOffsetException(String message) {
            super(message);
        }
    }

    Countries(String name, String language, int utcOffset){
        this.name = name;
        this.language = language;
        this.utcOffset = utcOffset;
    }

    @Override
    public String getLanguage() {
        return this.language;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Named byName(String name) {
        if(name == null) throw new IllegalArgumentException("Country name is null");

        for (Countries country : Countries.values()) {
            if (country.name.equalsIgnoreCase(name)) {
                return country;
            }
        }
        throw new IllegalArgumentException("This country is not in enum");
    }

    @Override
    public int getUtcOffset() {
        return utcOffset;
    }

    @Override
    public int diff(TimeOffset other) {
        if(other == null) throw new IllegalTimeOffsetException("Time offset object is null");
        if(Math.abs(other.getUtcOffset()) > 12) throw new IllegalTimeOffsetException("Time offset is incorrect");
        return other.getUtcOffset() - this.utcOffset;
    }
}
