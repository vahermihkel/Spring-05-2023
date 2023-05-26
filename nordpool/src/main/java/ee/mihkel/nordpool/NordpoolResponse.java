package ee.mihkel.nordpool;

import lombok.Data;

import java.util.ArrayList;

@Data
public class NordpoolResponse {
    private boolean success;
    private Andmed data;
}

@Data
class Andmed{
    private ArrayList<TimestampPrice> ee;
    private ArrayList<TimestampPrice> fi;
    private ArrayList<TimestampPrice> lv;
    private ArrayList<TimestampPrice> lt;
}

@Data
class TimestampPrice{
    private int timestamp;
    private double price;
}
