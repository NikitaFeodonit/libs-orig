/*
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/licenses/publicdomain
 */

import junit.framework.*;
import edu.emory.mathcs.backport.java.util.*;
import edu.emory.mathcs.backport.java.util.concurrent.*;
import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Random;
import java.util.BitSet;
import java.util.NoSuchElementException;

public class TreeMapTest extends JSR166TestCase {
    public static void main(String[] args) {
	junit.textui.TestRunner.run (suite());
    }
    public static Test suite() {
	return new TestSuite(TreeMapTest.class);
    }

    /**
     * Create a map from Integers 1-5 to Strings "A"-"E".
     */
    private static TreeMap map5() {
	TreeMap map = new TreeMap();
        assertTrue(map.isEmpty());
	map.put(one, "A");
	map.put(five, "E");
	map.put(three, "C");
	map.put(two, "B");
	map.put(four, "D");
        assertFalse(map.isEmpty());
        assertEquals(5, map.size());
	return map;
    }

    /**
     *  clear removes all pairs
     */
    public void testClear() {
        TreeMap map = map5();
	map.clear();
	assertEquals(map.size(), 0);
    }

    /**
     *
     */
    public void testConstructFromSorted() {
        TreeMap map = map5();
        TreeMap map2 = new TreeMap(map);
        assertEquals(map, map2);
    }

    /**
     *  Maps with same contents are equal
     */
    public void testEquals() {
        TreeMap map1 = map5();
        TreeMap map2 = map5();
        assertEquals(map1, map2);
        assertEquals(map2, map1);
	map1.clear();
        assertFalse(map1.equals(map2));
        assertFalse(map2.equals(map1));
    }

    /**
     *  containsKey returns true for contained key
     */
    public void testContainsKey() {
        TreeMap map = map5();
	assertTrue(map.containsKey(one));
        assertFalse(map.containsKey(zero));
    }

    /**
     *  containsValue returns true for held values
     */
    public void testContainsValue() {
        TreeMap map = map5();
	assertTrue(map.containsValue("A"));
        assertFalse(map.containsValue("Z"));
    }

    /**
     *  get returns the correct element at the given key,
     *  or null if not present
     */
    public void testGet() {
        TreeMap map = map5();
	assertEquals("A", (String)map.get(one));
        TreeMap empty = new TreeMap();
        assertNull(empty.get(one));
    }

    /**
     *  isEmpty is true of empty map and false for non-empty
     */
    public void testIsEmpty() {
        TreeMap empty = new TreeMap();
        TreeMap map = map5();
	assertTrue(empty.isEmpty());
        assertFalse(map.isEmpty());
    }

    /**
     *   firstKey returns first key
     */
    public void testFirstKey() {
        TreeMap map = map5();
	assertEquals(one, map.firstKey());
    }

    /**
     *   lastKey returns last key
     */
    public void testLastKey() {
        TreeMap map = map5();
	assertEquals(five, map.lastKey());
    }


    /**
     *  keySet.toArray returns contains all keys
     */
    public void testKeySetToArray() {
        TreeMap map = map5();
	Set s = map.keySet();
        Object[] ar = s.toArray();
        assertTrue(s.containsAll(Arrays.asList(ar)));
	assertEquals(5, ar.length);
        ar[0] = m10;
        assertFalse(s.containsAll(Arrays.asList(ar)));
    }

    /**
     *  descendingkeySet.toArray returns contains all keys
     */
    public void testDescendingKeySetToArray() {
        TreeMap map = map5();
	Set s = map.descendingKeySet();
        Object[] ar = s.toArray();
	assertEquals(5, ar.length);
        assertTrue(s.containsAll(Arrays.asList(ar)));
        ar[0] = m10;
        assertFalse(s.containsAll(Arrays.asList(ar)));
    }

    /**
     *   keySet returns a Set containing all the keys
     */
    public void testKeySet() {
        TreeMap map = map5();
	Set s = map.keySet();
	assertEquals(5, s.size());
	assertTrue(s.contains(one));
	assertTrue(s.contains(two));
	assertTrue(s.contains(three));
	assertTrue(s.contains(four));
	assertTrue(s.contains(five));
    }

    /**
     *   keySet is ordered
     */
    public void testKeySetOrder() {
        TreeMap map = map5();
	Set s = map.keySet();
        Iterator i = s.iterator();
        Integer last = (Integer)i.next();
        assertEquals(last, one);
        while (i.hasNext()) {
            Integer k = (Integer)i.next();
            assertTrue(last.compareTo(k) < 0);
            last = k;
        }
    }

    /**
     *   descendingKeySet is ordered
     */
    public void testDescendingKeySetOrder() {
        TreeMap map = map5();
	Set s = map.descendingKeySet();
        Iterator i = s.iterator();
        Integer last = (Integer)i.next();
        assertEquals(last, five);
        while (i.hasNext()) {
            Integer k = (Integer)i.next();
            assertTrue(last.compareTo(k) > 0);
            last = k;
        }
    }

    /**
     * values collection contains all values
     */
    public void testValues() {
        TreeMap map = map5();
	Collection s = map.values();
	assertEquals(5, s.size());
	assertTrue(s.contains("A"));
	assertTrue(s.contains("B"));
	assertTrue(s.contains("C"));
	assertTrue(s.contains("D"));
	assertTrue(s.contains("E"));
    }

    /**
     * entrySet contains all pairs
     */
    public void testEntrySet() {
        TreeMap map = map5();
	Set s = map.entrySet();
	assertEquals(5, s.size());
        Iterator it = s.iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            assertTrue(
                       (e.getKey().equals(one) && e.getValue().equals("A")) ||
                       (e.getKey().equals(two) && e.getValue().equals("B")) ||
                       (e.getKey().equals(three) && e.getValue().equals("C")) ||
                       (e.getKey().equals(four) && e.getValue().equals("D")) ||
                       (e.getKey().equals(five) && e.getValue().equals("E")));
        }
    }

    /**
     * descendingEntrySet contains all pairs
     */
    public void testDescendingEntrySet() {
        TreeMap map = map5();
	Set s = map.descendingMap().entrySet();
	assertEquals(5, s.size());
        Iterator it = s.iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            assertTrue(
                       (e.getKey().equals(one) && e.getValue().equals("A")) ||
                       (e.getKey().equals(two) && e.getValue().equals("B")) ||
                       (e.getKey().equals(three) && e.getValue().equals("C")) ||
                       (e.getKey().equals(four) && e.getValue().equals("D")) ||
                       (e.getKey().equals(five) && e.getValue().equals("E")));
        }
    }

    /**
     *  entrySet.toArray contains all entries
     */
    public void testEntrySetToArray() {
        TreeMap map = map5();
	Set s = map.entrySet();
        Object[] ar = s.toArray();
        assertEquals(5, ar.length);
        for (int i = 0; i < 5; ++i) {
            assertTrue(map.containsKey(((Map.Entry)(ar[i])).getKey()));
            assertTrue(map.containsValue(((Map.Entry)(ar[i])).getValue()));
        }
    }

    /**
     *  descendingEntrySet.toArray contains all entries
     */
    public void testDescendingEntrySetToArray() {
        TreeMap map = map5();
	Set s = map.descendingMap().entrySet();
        Object[] ar = s.toArray();
        assertEquals(5, ar.length);
        for (int i = 0; i < 5; ++i) {
            assertTrue(map.containsKey(((Map.Entry)(ar[i])).getKey()));
            assertTrue(map.containsValue(((Map.Entry)(ar[i])).getValue()));
        }
    }

    /**
     *   putAll  adds all key-value pairs from the given map
     */
    public void testPutAll() {
        TreeMap empty = new TreeMap();
        TreeMap map = map5();
	empty.putAll(map);
	assertEquals(5, empty.size());
	assertTrue(empty.containsKey(one));
	assertTrue(empty.containsKey(two));
	assertTrue(empty.containsKey(three));
	assertTrue(empty.containsKey(four));
	assertTrue(empty.containsKey(five));
    }

    /**
     *   remove removes the correct key-value pair from the map
     */
    public void testRemove() {
        TreeMap map = map5();
	map.remove(five);
	assertEquals(4, map.size());
	assertFalse(map.containsKey(five));
    }

    /**
     * lowerEntry returns preceding entry.
     */
    public void testLowerEntry() {
        TreeMap map = map5();
        Map.Entry e1 = map.lowerEntry(three);
        assertEquals(two, e1.getKey());

        Map.Entry e2 = map.lowerEntry(six);
        assertEquals(five, e2.getKey());

        Map.Entry e3 = map.lowerEntry(one);
        assertNull(e3);

        Map.Entry e4 = map.lowerEntry(zero);
        assertNull(e4);

    }

    /**
     * higherEntry returns next entry.
     */
    public void testHigherEntry() {
        TreeMap map = map5();
        Map.Entry e1 = map.higherEntry(three);
        assertEquals(four, e1.getKey());

        Map.Entry e2 = map.higherEntry(zero);
        assertEquals(one, e2.getKey());

        Map.Entry e3 = map.higherEntry(five);
        assertNull(e3);

        Map.Entry e4 = map.higherEntry(six);
        assertNull(e4);

    }

    /**
     * floorEntry returns preceding entry.
     */
    public void testFloorEntry() {
        TreeMap map = map5();
        Map.Entry e1 = map.floorEntry(three);
        assertEquals(three, e1.getKey());

        Map.Entry e2 = map.floorEntry(six);
        assertEquals(five, e2.getKey());

        Map.Entry e3 = map.floorEntry(one);
        assertEquals(one, e3.getKey());

        Map.Entry e4 = map.floorEntry(zero);
        assertNull(e4);

    }

    /**
     * ceilingEntry returns next entry.
     */
    public void testCeilingEntry() {
        TreeMap map = map5();
        Map.Entry e1 = map.ceilingEntry(three);
        assertEquals(three, e1.getKey());

        Map.Entry e2 = map.ceilingEntry(zero);
        assertEquals(one, e2.getKey());

        Map.Entry e3 = map.ceilingEntry(five);
        assertEquals(five, e3.getKey());

        Map.Entry e4 = map.ceilingEntry(six);
        assertNull(e4);

    }


    /**
     * lowerKey returns preceding element
     */
    public void testLowerKey() {
        TreeMap q = map5();
        Object e1 = q.lowerKey(three);
        assertEquals(two, e1);

        Object e2 = q.lowerKey(six);
        assertEquals(five, e2);

        Object e3 = q.lowerKey(one);
        assertNull(e3);

        Object e4 = q.lowerKey(zero);
        assertNull(e4);

    }

    /**
     * higherKey returns next element
     */
    public void testHigherKey() {
        TreeMap q = map5();
        Object e1 = q.higherKey(three);
        assertEquals(four, e1);

        Object e2 = q.higherKey(zero);
        assertEquals(one, e2);

        Object e3 = q.higherKey(five);
        assertNull(e3);

        Object e4 = q.higherKey(six);
        assertNull(e4);

    }

    /**
     * floorKey returns preceding element
     */
    public void testFloorKey() {
        TreeMap q = map5();
        Object e1 = q.floorKey(three);
        assertEquals(three, e1);

        Object e2 = q.floorKey(six);
        assertEquals(five, e2);

        Object e3 = q.floorKey(one);
        assertEquals(one, e3);

        Object e4 = q.floorKey(zero);
        assertNull(e4);

    }

    /**
     * ceilingKey returns next element
     */
    public void testCeilingKey() {
        TreeMap q = map5();
        Object e1 = q.ceilingKey(three);
        assertEquals(three, e1);

        Object e2 = q.ceilingKey(zero);
        assertEquals(one, e2);

        Object e3 = q.ceilingKey(five);
        assertEquals(five, e3);

        Object e4 = q.ceilingKey(six);
        assertNull(e4);

    }

    /**
     * pollFirstEntry returns entries in order
     */
    public void testPollFirstEntry() {
        TreeMap map = map5();
        Map.Entry e = map.pollFirstEntry();
        assertEquals(one, e.getKey());
        assertEquals("A", e.getValue());
        e = map.pollFirstEntry();
        assertEquals(two, e.getKey());
        map.put(one, "A");
        e = map.pollFirstEntry();
        assertEquals(one, e.getKey());
        assertEquals("A", e.getValue());
        e = map.pollFirstEntry();
        assertEquals(three, e.getKey());
        map.remove(four);
        e = map.pollFirstEntry();
        assertEquals(five, e.getKey());
        try {
            e.setValue("A");
            shouldThrow();
        } catch (Exception ok) {
        }
        e = map.pollFirstEntry();
        assertNull(e);
    }

    /**
     * pollLastEntry returns entries in order
     */
    public void testPollLastEntry() {
        TreeMap map = map5();
        Map.Entry e = map.pollLastEntry();
        assertEquals(five, e.getKey());
        assertEquals("E", e.getValue());
        e = map.pollLastEntry();
        assertEquals(four, e.getKey());
        map.put(five, "E");
        e = map.pollLastEntry();
        assertEquals(five, e.getKey());
        assertEquals("E", e.getValue());
        e = map.pollLastEntry();
        assertEquals(three, e.getKey());
        map.remove(two);
        e = map.pollLastEntry();
        assertEquals(one, e.getKey());
        try {
            e.setValue("E");
            shouldThrow();
        } catch (Exception ok) {
        }
        e = map.pollLastEntry();
        assertNull(e);
    }

    /**
     *   size returns the correct values
     */
    public void testSize() {
        TreeMap map = map5();
        TreeMap empty = new TreeMap();
	assertEquals(0, empty.size());
	assertEquals(5, map.size());
    }

    /**
     * toString contains toString of elements
     */
    public void testToString() {
        TreeMap map = map5();
        String s = map.toString();
        for (int i = 1; i <= 5; ++i) {
            assertTrue(s.indexOf(String.valueOf(i)) >= 0);
        }
    }

    // Exception tests

    /**
     * get(null) of nonempty map throws NPE
     */
    public void testGet_NullPointerException() {
        try {
            TreeMap c = map5();
            c.get(null);
            shouldThrow();
        } catch(NullPointerException e){}
    }

    /**
     * containsKey(null) of nonempty map throws NPE
     */
    public void testContainsKey_NullPointerException() {
        try {
            TreeMap c = map5();
            c.containsKey(null);
            shouldThrow();
        } catch(NullPointerException e){}
    }

    /**
     * remove(null) throws NPE for nonempty map
     */
    public void testRemove1_NullPointerException() {
        try {
            TreeMap c = new TreeMap();
            c.put("sadsdf", "asdads");
            c.remove(null);
            shouldThrow();
        } catch(NullPointerException e){}
    }

    /**
     * A deserialized map equals original
     */
    public void testSerialization() {
        TreeMap q = map5();

        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream(10000);
            ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(bout));
            out.writeObject(q);
            out.close();

            ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
            ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(bin));
            TreeMap r = (TreeMap)in.readObject();
            assertEquals(q.size(), r.size());
            assertTrue(q.equals(r));
            assertTrue(r.equals(q));
        } catch(Exception e){
            e.printStackTrace();
            unexpectedException();
        }
    }

    /**
     * subMap returns map with keys in requested range
     */
    public void testSubMapContents() {
        TreeMap map = map5();
        NavigableMap sm = map.subMap(two, true, four, false);
        assertEquals(two, sm.firstKey());
        assertEquals(three, sm.lastKey());
        assertEquals(2, sm.size());
        assertFalse(sm.containsKey(one));
        assertTrue(sm.containsKey(two));
        assertTrue(sm.containsKey(three));
        assertFalse(sm.containsKey(four));
        assertFalse(sm.containsKey(five));
        Iterator i = sm.keySet().iterator();
        Object k;
        k = (Integer)(i.next());
        assertEquals(two, k);
        k = (Integer)(i.next());
        assertEquals(three, k);
        assertFalse(i.hasNext());
        Iterator r = sm.descendingKeySet().iterator();
        k = (Integer)(r.next());
        assertEquals(three, k);
        k = (Integer)(r.next());
        assertEquals(two, k);
        assertFalse(r.hasNext());

        Iterator j = sm.keySet().iterator();
        j.next();
        j.remove();
        assertFalse(map.containsKey(two));
        assertEquals(4, map.size());
        assertEquals(1, sm.size());
        assertEquals(three, sm.firstKey());
        assertEquals(three, sm.lastKey());
        assertTrue(sm.remove(three) != null);
        assertTrue(sm.isEmpty());
        assertEquals(3, map.size());
    }

    public void testSubMapContents2() {
        TreeMap map = map5();
        NavigableMap sm = map.subMap(two, true, three, false);
        assertEquals(1, sm.size());
        assertEquals(two, sm.firstKey());
        assertEquals(two, sm.lastKey());
        assertFalse(sm.containsKey(one));
        assertTrue(sm.containsKey(two));
        assertFalse(sm.containsKey(three));
        assertFalse(sm.containsKey(four));
        assertFalse(sm.containsKey(five));
        Iterator i = sm.keySet().iterator();
        Object k;
        k = (Integer)(i.next());
        assertEquals(two, k);
        assertFalse(i.hasNext());
        Iterator r = sm.descendingKeySet().iterator();
        k = (Integer)(r.next());
        assertEquals(two, k);
        assertFalse(r.hasNext());

        Iterator j = sm.keySet().iterator();
        j.next();
        j.remove();
        assertFalse(map.containsKey(two));
        assertEquals(4, map.size());
        assertEquals(0, sm.size());
        assertTrue(sm.isEmpty());
        assertTrue(sm.remove(three) == null);
        assertEquals(4, map.size());
    }

    /**
     * headMap returns map with keys in requested range
     */
    public void testHeadMapContents() {
        TreeMap map = map5();
        NavigableMap sm = map.headMap(four, false);
        assertTrue(sm.containsKey(one));
        assertTrue(sm.containsKey(two));
        assertTrue(sm.containsKey(three));
        assertFalse(sm.containsKey(four));
        assertFalse(sm.containsKey(five));
        Iterator i = sm.keySet().iterator();
        Object k;
        k = (Integer)(i.next());
        assertEquals(one, k);
        k = (Integer)(i.next());
        assertEquals(two, k);
        k = (Integer)(i.next());
        assertEquals(three, k);
        assertFalse(i.hasNext());
        sm.clear();
        assertTrue(sm.isEmpty());
        assertEquals(2, map.size());
        assertEquals(four, map.firstKey());
    }

    /**
     * headMap returns map with keys in requested range
     */
    public void testTailMapContents() {
        TreeMap map = map5();
        NavigableMap sm = map.tailMap(two, true);
        assertFalse(sm.containsKey(one));
        assertTrue(sm.containsKey(two));
        assertTrue(sm.containsKey(three));
        assertTrue(sm.containsKey(four));
        assertTrue(sm.containsKey(five));
        Iterator i = sm.keySet().iterator();
        Object k;
        k = (Integer)(i.next());
        assertEquals(two, k);
        k = (Integer)(i.next());
        assertEquals(three, k);
        k = (Integer)(i.next());
        assertEquals(four, k);
        k = (Integer)(i.next());
        assertEquals(five, k);
        assertFalse(i.hasNext());
        Iterator r = sm.descendingKeySet().iterator();
        k = (Integer)(r.next());
        assertEquals(five, k);
        k = (Integer)(r.next());
        assertEquals(four, k);
        k = (Integer)(r.next());
        assertEquals(three, k);
        k = (Integer)(r.next());
        assertEquals(two, k);
        assertFalse(r.hasNext());

        Iterator ei = sm.entrySet().iterator();
        Map.Entry e;
        e = (Map.Entry)(ei.next());
        assertEquals(two, e.getKey());
        assertEquals("B", e.getValue());
        e = (Map.Entry)(ei.next());
        assertEquals(three, e.getKey());
        assertEquals("C", e.getValue());
        e = (Map.Entry)(ei.next());
        assertEquals(four, e.getKey());
        assertEquals("D", e.getValue());
        e = (Map.Entry)(ei.next());
        assertEquals(five, e.getKey());
        assertEquals("E", e.getValue());
        assertFalse(i.hasNext());

        NavigableMap ssm = sm.tailMap(four, true);
        assertEquals(four, ssm.firstKey());
        assertEquals(five, ssm.lastKey());
        assertTrue(ssm.remove(four) != null);
        assertEquals(1, ssm.size());
        assertEquals(3, sm.size());
        assertEquals(4, map.size());
    }

    Random rnd = new Random(666);
    BitSet bs;

    /**
     * Submaps of submaps subdivide correctly
     */
    public void testRecursiveSubMaps() {
        int mapSize = 1000;
        Class cl = TreeMap.class;
        NavigableMap map = newMap(cl);
        bs = new BitSet(mapSize);

        populate(map, mapSize);
        check(map,                 0, mapSize - 1, true);
        check(map.descendingMap(), 0, mapSize - 1, false);

        mutateMap(map, 0, mapSize - 1);
        check(map,                 0, mapSize - 1, true);
        check(map.descendingMap(), 0, mapSize - 1, false);

        bashSubMap(map.subMap(new Integer(0), true, new Integer(mapSize), false),
                   0, mapSize - 1, true);
    }

    static NavigableMap newMap(Class cl) {
        NavigableMap result = null;
        try {
            result = (NavigableMap) cl.newInstance();
        } catch(Exception e) {
            fail();
        }
        assertEquals(result.size(), 0);
        assertFalse(result.keySet().iterator().hasNext());
        return result;
    }

    void populate(NavigableMap map, int limit) {
        for (int i = 0, n = 2 * limit / 3; i < n; i++) {
            int key = rnd.nextInt(limit);
            put(map, key);
        }
    }

    void mutateMap(NavigableMap map, int min, int max) {
        int size = map.size();
        int rangeSize = max - min + 1;

        // Remove a bunch of entries directly
        for (int i = 0, n = rangeSize / 2; i < n; i++) {
            remove(map, min - 5 + rnd.nextInt(rangeSize + 10));
        }

        // Remove a bunch of entries with iterator
        for(Iterator it = map.keySet().iterator(); it.hasNext(); ) {
            if (rnd.nextBoolean()) {
                bs.clear(((Integer)it.next()).intValue());
                it.remove();
            }
        }

        // Add entries till we're back to original size
        while (map.size() < size) {
            int key = min + rnd.nextInt(rangeSize);
            assertTrue(key >= min && key<= max);
            put(map, key);
        }
    }

    void mutateSubMap(NavigableMap map, int min, int max) {
        int size = map.size();
        int rangeSize = max - min + 1;

        // Remove a bunch of entries directly
        for (int i = 0, n = rangeSize / 2; i < n; i++) {
            remove(map, min - 5 + rnd.nextInt(rangeSize + 10));
        }

        // Remove a bunch of entries with iterator
        for(Iterator it = map.keySet().iterator(); it.hasNext(); ) {
            if (rnd.nextBoolean()) {
                bs.clear(((Integer)it.next()).intValue());
                it.remove();
            }
        }

        // Add entries till we're back to original size
        while (map.size() < size) {
            int key = min - 5 + rnd.nextInt(rangeSize + 10);
            if (key >= min && key<= max) {
                put(map, key);
            } else {
                try {
                    map.put(new Integer(key), new Integer(2 * key));
                    fail();
                } catch(IllegalArgumentException e) {
                    // expected
                }
            }
        }
    }

    void put(NavigableMap map, int key) {
        if (map.put(new Integer(key), new Integer(2 * key)) == null)
            bs.set(key);
    }

    void remove(NavigableMap map, int key) {
        if (map.remove(new Integer(key)) != null)
            bs.clear(key);
    }

    void bashSubMap(NavigableMap map,
                    int min, int max, boolean ascending) {
        check(map, min, max, ascending);
        check(map.descendingMap(), min, max, !ascending);

        mutateSubMap(map, min, max);
        check(map, min, max, ascending);
        check(map.descendingMap(), min, max, !ascending);

        // Recurse
        if (max - min < 2)
            return;
        int midPoint = (min + max) / 2;

        // headMap - pick direction and endpoint inclusion randomly
        boolean incl = rnd.nextBoolean();
        NavigableMap hm = map.headMap(new Integer(midPoint), incl);
        if (ascending) {
            if (rnd.nextBoolean())
                bashSubMap(hm, min, midPoint - (incl ? 0 : 1), true);
            else
                bashSubMap(hm.descendingMap(), min, midPoint - (incl ? 0 : 1),
                           false);
        } else {
            if (rnd.nextBoolean())
                bashSubMap(hm, midPoint + (incl ? 0 : 1), max, false);
            else
                bashSubMap(hm.descendingMap(), midPoint + (incl ? 0 : 1), max,
                           true);
        }

        // tailMap - pick direction and endpoint inclusion randomly
        incl = rnd.nextBoolean();
        NavigableMap tm = map.tailMap(new Integer(midPoint),incl);
        if (ascending) {
            if (rnd.nextBoolean())
                bashSubMap(tm, midPoint + (incl ? 0 : 1), max, true);
            else
                bashSubMap(tm.descendingMap(), midPoint + (incl ? 0 : 1), max,
                           false);
        } else {
            if (rnd.nextBoolean()) {
                bashSubMap(tm, min, midPoint - (incl ? 0 : 1), false);
            } else {
                bashSubMap(tm.descendingMap(), min, midPoint - (incl ? 0 : 1),
                           true);
            }
        }

        // subMap - pick direction and endpoint inclusion randomly
        int rangeSize = max - min + 1;
        int[] endpoints = new int[2];
        endpoints[0] = min + rnd.nextInt(rangeSize);
        endpoints[1] = min + rnd.nextInt(rangeSize);
        Arrays.sort(endpoints);
        boolean lowIncl = rnd.nextBoolean();
        boolean highIncl = rnd.nextBoolean();
        if (ascending) {
            NavigableMap sm = map.subMap(
                new Integer(endpoints[0]), lowIncl, new Integer(endpoints[1]), highIncl);
            if (rnd.nextBoolean())
                bashSubMap(sm, endpoints[0] + (lowIncl ? 0 : 1),
                           endpoints[1] - (highIncl ? 0 : 1), true);
            else
                bashSubMap(sm.descendingMap(), endpoints[0] + (lowIncl ? 0 : 1),
                           endpoints[1] - (highIncl ? 0 : 1), false);
        } else {
            NavigableMap sm = map.subMap(
                new Integer(endpoints[1]), highIncl, new Integer(endpoints[0]), lowIncl);
            if (rnd.nextBoolean())
                bashSubMap(sm, endpoints[0] + (lowIncl ? 0 : 1),
                           endpoints[1] - (highIncl ? 0 : 1), false);
            else
                bashSubMap(sm.descendingMap(), endpoints[0] + (lowIncl ? 0 : 1),
                           endpoints[1] - (highIncl ? 0 : 1), true);
        }
    }

    /**
     * min and max are both inclusive.  If max < min, interval is empty.
     */
    void check(NavigableMap map,
                      final int min, final int max, final boolean ascending) {
       class ReferenceSet {
            int lower(int key) {
                return ascending ? lowerAscending(key) : higherAscending(key);
            }
            int floor(int key) {
                return ascending ? floorAscending(key) : ceilingAscending(key);
            }
            int ceiling(int key) {
                return ascending ? ceilingAscending(key) : floorAscending(key);
            }
            int higher(int key) {
                return ascending ? higherAscending(key) : lowerAscending(key);
            }
            int first() {
                return ascending ? firstAscending() : lastAscending();
            }
            int last() {
                return ascending ? lastAscending() : firstAscending();
            }
            int lowerAscending(int key) {
                return floorAscending(key - 1);
            }
            int floorAscending(int key) {
                if (key < min)
                    return -1;
                else if (key > max)
                    key = max;

                // BitSet should support this! Test would run much faster
                while (key >= min) {
                    if (bs.get(key))
                        return(key);
                    key--;
                }
                return -1;
            }
            int ceilingAscending(int key) {
                if (key < min)
                    key = min;
                else if (key > max)
                    return -1;
                int result = bs.nextSetBit(key);
                return result > max ? -1 : result;
            }
            int higherAscending(int key) {
                return ceilingAscending(key + 1);
            }
            private int firstAscending() {
                int result = ceilingAscending(min);
                return result > max ? -1 : result;
            }
            private int lastAscending() {
                int result = floorAscending(max);
                return result < min ? -1 : result;
            }
        }
        ReferenceSet rs = new ReferenceSet();

        // Test contents using containsKey
        int size = 0;
        for (int i = min; i <= max; i++) {
            boolean bsContainsI = bs.get(i);
            assertEquals(bsContainsI, map.containsKey(new Integer(i)));
            if (bsContainsI)
                size++;
        }
        assertEquals(map.size(), size);

        // Test contents using contains keySet iterator
        int size2 = 0;
        int previousKey = -1;
        for (Iterator itr = map.keySet().iterator(); itr.hasNext();) {
            int key = ((Integer)itr.next()).intValue();
            assertTrue(bs.get(key));
            size2++;
            assertTrue(previousKey < 0 ||
                (ascending ? key - previousKey > 0 : key - previousKey < 0));
            previousKey = key;
        }
        assertEquals(size2, size);

        // Test navigation ops
        for (int key = min - 1; key <= max + 1; key++) {
            assertEq((Integer)map.lowerKey(new Integer(key)), rs.lower(key));
            assertEq((Integer)map.floorKey(new Integer(key)), rs.floor(key));
            assertEq((Integer)map.higherKey(new Integer(key)), rs.higher(key));
            assertEq((Integer)map.ceilingKey(new Integer(key)), rs.ceiling(key));
        }

        // Test extrema
        if (map.size() != 0) {
            assertEq((Integer)map.firstKey(), rs.first());
            assertEq((Integer)map.lastKey(), rs.last());
        } else {
            assertEq(new Integer(rs.first()), -1);
            assertEq(new Integer(rs.last()),  -1);
            try {
                map.firstKey();
                fail();
            } catch(NoSuchElementException e) {
                // expected
            }
            try {
                map.lastKey();
                fail();
            } catch(NoSuchElementException e) {
                // expected
            }
        }
    }

    static void assertEq(Integer i, int j) {
        if (i == null)
            assertEquals(j, -1);
        else
            assertEquals(i.intValue(), j);
    }

    static boolean eq(Integer i, int j) {
        return i == null ? j == -1 : i.intValue() == j;
    }

}
