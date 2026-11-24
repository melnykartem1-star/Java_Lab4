import HardTask.Lists.MyArrayList;
import HardTask.Lists.MyLinkedList;
import HardTask.Maps.LRUCache;
import HardTask.Sets.MyLinkedHashSet;

void main(){
    testArrayList();
    IO.println();
    testLinkedList();
    IO.println();
    testLinkedHashSet();
    IO.println();
    testLRUCache();
}

public static void testLinkedHashSet() {
    MyLinkedHashSet<String> set = new MyLinkedHashSet<>();

    System.out.println(set.add("Artem"));
    System.out.println(set.add("Ivan"));
    System.out.println(set.add("Seva"));
    System.out.println(set.add("Max"));

    System.out.println(set.add("Artem"));

    System.out.println(set.contains("Ivan"));
    System.out.println(set.remove("Seva"));
    System.out.println(set.contains("Seva"));

    System.out.println(set.size());
}

public static void testLRUCache() {
    LRUCache<String, String> cache = new LRUCache<>(3);

    cache.put("A", "Walter");
    cache.put("B", "Jesse");
    cache.put("C", "Mike");

    System.out.println(cache.size());

    System.out.println(cache.get("A"));

    cache.put("D", "Gus");

    System.out.println(cache.get("B"));
    System.out.println(cache.get("A"));
    System.out.println(cache.get("C"));
    System.out.println(cache.get("D"));

    System.out.println(cache.size());
}

public static void testLinkedList(){
    MyLinkedList<String> myTestList1 = new MyLinkedList<>();
    myTestList1.add("Josh");
    myTestList1.add("Morgan");

    MyLinkedList<String> myTestList2 = new MyLinkedList<>();
    myTestList2.add("Rick");
    myTestList2.add("Deb");

    MyLinkedList<String> myList = new MyLinkedList<>();
    myList.add("Artem");
    myList.add(0, "Sevil");
    myList.addAll(myTestList1);
    myList.addAll(2, myTestList2);

    IO.println(myList.get(2));
    IO.println(myList.remove(2));
    myList.set(2, "Fake");
    IO.println(myList.indexOf("Fake"));
    IO.println(myList.size());
    IO.println(Arrays.toString(myList.toArray()));
}

public static void testArrayList(){
    MyArrayList<String> myTestList1 = new MyArrayList<>();
    myTestList1.add("Josh");
    myTestList1.add("Morgan");

    MyArrayList<String> myTestList2 = new MyArrayList<>();
    myTestList2.add("Rick");
    myTestList2.add("Deb");

    MyArrayList<String> myList = new MyArrayList<>();
    myList.add("Artem");
    myList.add(0, "Sevil");
    myList.addAll(myTestList1);
    myList.addAll(2, myTestList2);

    IO.println(myList.get(2));
    IO.println(myList.remove(2));
    myList.set(2, "Fake");
    IO.println(myList.indexOf("Fake"));
    IO.println(myList.size());
    IO.println(Arrays.toString(myList.toArray()));

}