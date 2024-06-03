package Lab;

import java.util.HashSet;
import java.util.Iterator;

public class HashsetDemo {

    // set을 parameter로 건네면 elements를 출력함
    private static void outputSet(HashSet<String> set) {
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next() + " ");
        }
    }

    public static void main(String[] args) {
        boolean doseIEExist;
        boolean doesComputerExist;
        String IEDept = "Industrial_Engineering";
        String CSEDpet = "Computer_Science_&_Engineering";

        HashSet<String> depInEngineeringCollegeSet = new HashSet<>() {
            {
                add("General_Engineering");
                add("Mechanical_Engineering");
                add("Architecture");
                add("Material_Science");
                add("Environment_Engineering");
                add("Civil_Engineering");
            }
        };

        HashSet<String> depInITCollegeSet = new HashSet<>() {
            {
                add("Electrical_Engineering");
                add("Computer_Science_&_Engineering");
                add("General_Engineering");
                add("Electronics_Engineering");
            }
        };

        System.out.println("Departments in college of Engineering: ");
        outputSet(depInEngineeringCollegeSet);
        System.out.println();

        System.out.println("Departments in college of IT Engineering: ");
        outputSet(depInITCollegeSet);
        System.out.println();

        doseIEExist = depInEngineeringCollegeSet.contains(IEDept);
        doesComputerExist = depInITCollegeSet.contains(CSEDpet);

        System.out.println("'Industrial_Engineering' in set 'deptInEngineeringCollegeSet?' " + doseIEExist);
        System.out.println("'Computer_Science_&_Engineering' in set 'deptInITCollegeSet?' " + doesComputerExist);

        System.out.println();

        HashSet<String> allEngineeringDepts = new HashSet<>();
        HashSet<String> commonDepts = new HashSet<>();

        // hashset collection clone
        HashSet<String> depInEngineerClone = new HashSet<>();
        depInEngineerClone = (HashSet<String>) depInEngineeringCollegeSet.clone();
        HashSet<String> depInITClone = new HashSet<>();
        depInITClone = (HashSet<String>) depInITCollegeSet.clone();


        depInEngineerClone.addAll(depInITClone);
        allEngineeringDepts.addAll(depInEngineerClone);


        System.out.println("Union of 'depInEngineeringCollegeSet' and 'depInITCollegeSet':");
        outputSet(depInEngineerClone);
        System.out.println();

        System.out.println("Intersection of 'depInEngineeringCollegeSet' and 'depInITCollegeSet':");
        depInEngineeringCollegeSet.retainAll(depInITCollegeSet);
        outputSet(depInEngineeringCollegeSet);

    }






}
