import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionMain {

    public static void main(String[] args) {

        String classToUse = args[0];
        String method = args[1];
        double x = Double.parseDouble(args[2]);
        double y = Double.parseDouble(args[3]);

        try {
            Class<?> cl = Class.forName(classToUse);
            Method me = cl.getDeclaredMethod(method, double.class, double.class);
            System.out.println(me.invoke(new Object(), x, y));
        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    public static double add(double x, double y) {
        return x + y;
    }

}
