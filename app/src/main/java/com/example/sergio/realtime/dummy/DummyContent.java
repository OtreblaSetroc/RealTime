package com.example.sergio.realtime.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Helper class for providing sample nombre for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Sos> ITEMS = new ArrayList<Sos>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Sos> ITEM_MAP = new HashMap<String, Sos>();

    private static final int COUNT = 0;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    public static void addItem(Sos item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
    public  static  void updateItem(Sos sos) {
        ITEMS.set(ITEMS.indexOf(sos),sos);
        ITEM_MAP.put(sos.getId(),sos);
    }
    public  static  void  deteleItem(Sos sos){
        ITEMS.remove(sos);
        ITEM_MAP.remove(sos);
    }

    private static Sos createDummyItem(int position) {
        return new Sos(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of nombre.
     */
    public static class Sos {
        private   String id;
        private   String nombre;
        private   String frente;

        public Sos(String id, String nombre, String frente) {
            this.id = id;
            this.nombre = nombre;
            this.frente = frente;
        }

        public Sos(String nombre, String frente) {
            this.nombre = nombre;
            this.frente = frente;
        }

        public Sos() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getFrente() {
            return frente;
        }

        public void setFrente(String frente) {
            this.frente = frente;
        }

        @Override
        public String toString() {
            return nombre;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Sos sos = (Sos) o;
            return     id!=null ?id.equals(sos.id):sos.id==null;
        }

        @Override
        public int hashCode() {

            return id!=null ? id.hashCode():0;
        }
    }
}
