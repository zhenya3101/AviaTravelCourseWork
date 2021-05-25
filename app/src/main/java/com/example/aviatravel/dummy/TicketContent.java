package com.example.aviatravel.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketContent {
    public static class DummyItem {
        public final String id;
        public final String number;
        public final String pas;
        public final String date;
        public final String time;
        public final String from;
        public final String to;
        public final String flight;

        public DummyItem(String id, String number, String pas,String date, String time, String from, String to, String flight) {
            this.id = id;
            this.number = number;
            this.pas = pas;
            this.date = date;
            this.time = time;
            this.from = from;
            this.to = to;
            this.flight = flight;
        }
    }
}
