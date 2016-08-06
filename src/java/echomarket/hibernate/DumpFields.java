/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echomarket.hibernate;


import java.lang.reflect.*;

public class DumpFields {
    public static void main(String[] args) {
        
        inspect(Borrowers.class);
    }
    static <T> void inspect(Class<T> klazz) {
        Field[] fields = klazz.getDeclaredFields();
        System.out.printf("%d fields:%n", fields.length);
        for (Field field : fields) {
            //System.out.printf("%s %s %s%n",
            //    Modifier.toString(field.getModifiers()), field.getType().getSimpleName(),field.getName());
            System.out.printf("\n" + field.getName());    
        }
    }
}

/// Source http://stackoverflow.com/questions/3333974/how-to-loop-over-a-class-attributes-in-java