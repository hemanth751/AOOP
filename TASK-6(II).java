import java.util.Arrays;

interface MinMax<T extends Comparable<T>> {
    T findMin();
    T findMax();
}

class ArrayMinMax<T extends Comparable<T>> implements MinMax<T> {
    private T[] array;

    public ArrayMinMax(T[] array) {
        this.array = array;
    }

    @Override
    public T findMin() {
        return Arrays.stream(array).min(Comparable::compareTo).orElse(null);
    }

    @Override
    public T findMax() {
        return Arrays.stream(array).max(Comparable::compareTo).orElse(null);
    }
}

public class GenericsMinMaxExample {
    public static <T extends Comparable<T>> void bubbleSort(T[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j].compareTo(array[j + 1]) > 0) {
                    T temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        Integer[] intArray = {3, 5, 1, 8, 2};
        String[] strArray = {"Apple", "Mango", "Banana", "Peach"};
        Character[] charArray = {'z', 'a', 'u', 'b'};
        Float[] floatArray = {3.5f, 1.2f, 8.7f, 2.4f};
        
        bubbleSort(intArray);
        System.out.println("Sorted Integers: " + Arrays.toString(intArray));
        
        bubbleSort(strArray);
        System.out.println("Sorted Strings: " + Arrays.toString(strArray));
        
        bubbleSort(charArray);
        System.out.println("Sorted Characters: " + Arrays.toString(charArray));
        
        bubbleSort(floatArray);
        System.out.println("Sorted Floats: " + Arrays.toString(floatArray));
        
        ArrayMinMax<Integer> intMinMax = new ArrayMinMax<>(intArray);
        System.out.println("Integer - Min: " + intMinMax.findMin() + ", Max: " + intMinMax.findMax());
        
        ArrayMinMax<String> strMinMax = new ArrayMinMax<>(strArray);
        System.out.println("String - Min: " + strMinMax.findMin() + ", Max: " + strMinMax.findMax());
        
        ArrayMinMax<Character> charMinMax = new ArrayMinMax<>(charArray);
        System.out.println("Character - Min: " + charMinMax.findMin() + ", Max: " + charMinMax.findMax());
        
        ArrayMinMax<Float> floatMinMax = new ArrayMinMax<>(floatArray);
        System.out.println("Float - Min: " + floatMinMax.findMin() + ", Max: " + floatMinMax.findMax());
    }
}
