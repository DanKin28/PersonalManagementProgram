/******************************************************************************

- Final Project
- Daniel Kinahan

*******************************************************************************/
import java.util.*;
import java.util.regex.*;
import java.io.*;
import java.math.*;
import java.text.*;

public class FinalProject
{
    public static void main(String[] args)
    {
       System.out.println("\t\t\t\t  Welcome to my Personal Managment Program");
       System.out.println();
       System.out.println("Choose one of the options:");
       collegeList data = new collegeList();
       Scanner s = new Scanner(System.in);
       menu(s, data);
       
       System.out.print("Would you like to create the report? (Y/N): ");
       String report = s.nextLine();
       
       while(!report.toLowerCase().equals("y") && !report.toLowerCase().equals("n"))
       {
           System.out.print("Please type either y or n: ");
           report = s.nextLine();
       }
       
       if(report.toLowerCase().equals("y"))
       {
         FileWriter myWriter;
         Date currentDate = new Date();
         SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
         String formattedDate = formatter.format(currentDate); 
         
         System.out.print("Would like to sort your students by descending gpa or name (1 for gpa, 2 for name): ");
         String keep = s.nextLine();
         
         while(!keep.equals("1") && !keep.equals("2"))
         {
             System.out.print("Please type either 1 or 2: ");
             keep = s.nextLine();
         }
         
         try {
           
           myWriter = new FileWriter("report.txt");
           myWriter.write("Report created on " + formattedDate + "\n");
           myWriter.write("****************************\n\n");
           data.writeFaculty(myWriter);
           data.writeStaff(myWriter);
           data.writeStudent(myWriter, keep);
           myWriter.close();
           System.out.println("Report created and saved on your hard drive!");
         }
         catch(Exception e)
         {
           System.out.println("File could not be created");
         }
       }
       
       System.out.println("Goodbye!");
    }
    
    public static void menu(Scanner s, collegeList c)
    {
      char number = ' ';
      
      while(number != '8')
      {
        System.out.println();
        System.out.println("1- Enter the information a faculty");
        System.out.println("2- Enter the information of a student");
        System.out.println("3- Print tuition invoice for a student");
        System.out.println("4- Print faculty information");
        System.out.println("5- Enter the information of a staff member");
        System.out.println("6- Print the information of a staff member");
        System.out.println("7- Delete a person");
        System.out.println("8- Exit Program");
        System.out.println();
        System.out.print("\tEnter your selection: ");
        number = s.nextLine().charAt(0);
        
        
        switch(number)
        {
            case '1':
                c.setFaculty(s);
                break;
            case '2':
                c.setStudent(s);
                break;
            case '3':
                c.getInvoice(s);
                break;
            case '4':
                c.getFaculty(s);
                break;
            case '5':
                c.setStaff(s);
                break;
            case '6':
                c.getStaff(s);
                break;
            case '7':
                c.deletePerson(s);
                break;
            case '8':
                break;
            default:
                System.out.println("Invalid entry- please try again");
                System.out.println();
                break;
        }
      }
      
    }
    
}

/////////////////////////////////////////////
abstract class Person
{
  private String fullName, id;
  
  public Person(String fullName, String id)
  {
    setName(fullName);
    setID(id);
  }
  
  public void setName(String s)
  {
      fullName = s;
  }
  
  public void setID(String i)
  {
      id = i;
  }
  
  public String getName()
  {
      return fullName;
  }
  
  public String getID()
  {
      return id;
  }
  
  public double rounding(double d)
  {
    BigDecimal a = new BigDecimal(d);
    a = a.setScale(2, RoundingMode.DOWN);
    
    return a.doubleValue();
  }
  
  public String toString()
  {
      return fullName + "\n" + "ID: " + id + "\n";
  }
 
}

/////////////////////////////////////////////
class Student extends Person
{
   private double gpa, discount;
   private int creditHours;
   
   public Student(String fullName, String id, double gpa, int creditHours)
   {
     super(fullName, id);
     this.gpa = gpa;
     this.creditHours = creditHours;
     discount = 0.0;
   }
   
   public void setGPA(double g)
   {
       gpa = g;
   }
   
   public void setCreditHours(int c)
   {
       creditHours = c;
   }
   
   public double getGPA()
   {
       return gpa;
   }
   
   public int getCreditHours()
   {
       return creditHours;
   }
   
   private double getTuitionPrice()
   {
       double price = 236.45;
       price *= creditHours;
       
       if(gpa >= 3.85)
       {
           discount = price * 0.25;
           price -= discount;
           discount = super.rounding(discount);
       }
       
       return super.rounding(price) + 52.0;
   }
   
   public void tuitionInvoice()
   {
     System.out.println("---------------------------------------------------------------------------");
     System.out.println(super.getName() + "\t\t" + super.getID());
     System.out.println("Credit Hours: " + creditHours + "($236.45/credit hour)");
     System.out.println("Fees: $52");
     System.out.println("Total Payment (after discount): $" + getTuitionPrice() + "\t\t" + "($" + discount + " discount applied)");
     System.out.println("---------------------------------------------------------------------------");
   }
   
   @Override
   public String toString()
   {
     return super.toString() + "Gpa: " + gpa + "\n" + "Credit Hours: " + creditHours + "\n";  
   }
   
}

/////////////////////////////////////////////
abstract class Employee extends Person
{
   private String department;
   
   public Employee(String fullName, String id, String department)
   {
       super(fullName, id);
       setDepartment(department);
   }
   
   public void setDepartment(String d)
   {
       department = d.substring(0,1).toUpperCase() + d.substring(1).toLowerCase();
   }
   
   public String getDepartment()
   {
       return department;
   }
   
   @Override
   public String toString()
   {
       return super.toString();
   }
   
   abstract public void printInfo();

}

/////////////////////////////////////////////
class Faculty extends Employee
{
   private String rank;
   
   public Faculty(String fullName, String id, String department, String rank)
   {
       super(fullName, id, department);
       setRank(rank);
   }
   
   public void setRank(String r)
   {
       rank = r.substring(0,1).toUpperCase() + r.substring(1).toLowerCase();
   }
   
   public String getRank()
   {
       return rank;
   }
   
   @Override
   public String toString()
   {
      return super.toString() + rank + "," + super.getDepartment() + "\n";  
   }
   
   @Override
   public void printInfo()
   {
       System.out.println("---------------------------------------------------------------------------");
       System.out.println(super.getName() + "\t" + super.getID());
       System.out.println(super.getDepartment() + " Department, " + rank);
       System.out.println("---------------------------------------------------------------------------");
   }
}

/////////////////////////////////////////////
class Staff extends Employee
{
   private String status;
   
   public Staff(String fullName, String id, String department, String status)
   {
       super(fullName, id, department);
       setStatus(status);
   } 
   
   public void setStatus(String s)
   {
      if(s.toLowerCase().equals("f"))
      status = "Full Time";
      else
      status = "Part Time";
   }
   
   public String getStatus()
   {
       return status;
   }
   
   @Override
   public String toString()
   {
       return super.toString() + super.getDepartment() + ", " + status + "\n";
   }
   
   @Override
   public void printInfo()
   {
       System.out.println("---------------------------------------------------------------------------");
       System.out.println(super.getName() + "\t" + super.getID());
       System.out.println(super.getDepartment() + " Department, " + status);
       System.out.println("---------------------------------------------------------------------------");
   }
}

/////////////////////////////////////////////
class collegeList
{
    private ArrayList<Person> data;
    private String name, id;
    private Pattern pattern;
    
    public collegeList()
    {
        data = new ArrayList<Person>();
        pattern = Pattern.compile("[A-Za-z]{2}\\d{4}");
    }
    
    public void addData(Person p)
    {
        data.add(p);
    }
    
    public int getSize()
    {
       return data.size();
    }
    
    public void setStudent(Scanner s)
    {
      System.out.println();
      System.out.println("Enter the student info:");
      System.out.print("\tName of Student: ");
      name = s.nextLine();
      
      System.out.print("\tID: ");
      id = s.nextLine();
      
      Matcher matcher = pattern.matcher(id);
      while(!matcher.matches())
      {
         System.out.println("\tInvalid ID format. Must be LetterLetterDigitDigitDigitDigit");
         System.out.print("\tID: ");
         id = s.nextLine();
         matcher = pattern.matcher(id);
      }
      
      while(!isUniqueID(id))
      {
         System.out.println("\tID already taken - please enter a different one");
         System.out.print("\tID: ");
         id = s.nextLine();
         matcher = pattern.matcher(id);
      }
      
      System.out.print("\tGpa: ");
      double gpa = s.nextDouble();
      s.nextLine();
      
      System.out.print("\tCredit Hours: ");
      int creditHours = s.nextInt();
      s.nextLine();
      
      Student temp = new Student(name, id, gpa, creditHours);
      data.add(temp);
      
      System.out.println("Student added!");
      System.out.println();
    }
    
    public void setFaculty(Scanner s)
    {
      System.out.println();
      System.out.println("Enter the faculty info:\n");
      System.out.print("\tName of Faculty: ");
      name = s.nextLine();
      
      System.out.print("\tID: ");
      id = s.nextLine();
      
      Matcher matcher = pattern.matcher(id);
      while(!matcher.matches())
      {
         System.out.println("Invalid ID format. Must be LetterLetterDigitDigitDigitDigit");
         System.out.print("\tID: ");
         id = s.nextLine();
         matcher = pattern.matcher(id);
      }
      
      while(!isUniqueID(id))
      {
         System.out.println("ID already taken - please enter a different one");
         System.out.print("\tID: ");
         id = s.nextLine();
         matcher = pattern.matcher(id);
      }
      
      System.out.print("\tRank: ");
      String rank = s.nextLine();
      
      while(!isValidRank(rank))
      {
          System.out.println("\t\t\"" + rank + "\"" + " is invalid");
          System.out.print("\tRank: ");
          rank = s.nextLine();
      }
      
      System.out.print("\tDepartment: ");
      String department = s.nextLine();
      
      while(!isValidDepartment(department))
      {
          System.out.println("Invalid department - try again");
          System.out.print("\tDepartment: ");
          department = s.nextLine();
      }
      
      Faculty temp = new Faculty(name, id, department, rank);
      data.add(temp);
      
      System.out.println("Faculty added!");
      System.out.println();
    }
    
    public void setStaff(Scanner s)
    {
      System.out.println();
      System.out.println("Enter the staff info:");
      System.out.print("\tName of Staff: ");
      name = s.nextLine();
      
      System.out.print("\tID: ");
      id = s.nextLine();
      
      Matcher matcher = pattern.matcher(id);
      while(!matcher.matches())
      {
         System.out.println("Invalid ID format. Must be LetterLetterDigitDigitDigitDigit");
         System.out.print("\tID: ");
         id = s.nextLine();
         matcher = pattern.matcher(id);
      }
      
      while(!isUniqueID(id))
      {
         System.out.println("ID already taken - please enter a different one");
         System.out.print("\tID: ");
         id = s.nextLine();
         matcher = pattern.matcher(id);
      }
      
      System.out.print("\tDepartment: ");
      String department = s.nextLine();
      
      while(!isValidDepartment(department))
      {
          System.out.println("\tInvalid department - try again");
          System.out.print("\tDepartment: ");
          department = s.nextLine();
      }
      
      System.out.print("\tStatus, Enter P for Part Time, or Enter F for Full Time: ");
      String status = s.nextLine();
      
      while(!status.toLowerCase().equals("p") && !status.toLowerCase().equals("f"))
      {
         System.out.println("\tInvalid entry - please Enter P for Part Time, or Enter F for Full Time");
         System.out.print("\tStatus: ");
         status = s.nextLine();
      }
      
      Staff temp = new Staff(name, id, department, status);
      data.add(temp);
      
      System.out.println("Staff added!");
      System.out.println();
    }
    
    public void getInvoice(Scanner s)
    {
      System.out.println();
      System.out.print("\tEnter the student’s id: ");
      id = s.nextLine();
      
      for(Person p: data)
      {
          if(p instanceof Student)
          {
              if(p.getID().equals(id))
              {
                Student temp = (Student) p;
                System.out.println("Here is the tuition information for " + temp.getName());
                System.out.println();
                temp.tuitionInvoice();
                return;
              }
          }
      }
      
      System.out.println("\tNo student matched!");
      System.out.println();
    }
    
   
    public void getFaculty(Scanner s)
    {
      System.out.println();
      System.out.print("\tEnter the faculty’s id: ");
      id = s.nextLine();
      
      for(Person p: data)
      {
          if(p instanceof Faculty)
          {
              if(p.getID().equals(id))
              {
                Faculty temp = (Faculty) p;
                System.out.println();
                temp.printInfo();
                return;
              }
          }
      }
      
      System.out.println("\tNo faculty matched!");
      System.out.println(); 
    }
    
    public void getStaff(Scanner s)
    {
      System.out.println();
      System.out.print("\tEnter the staff’s id: ");
      id = s.nextLine();
      
      for(Person p: data)
      {
          if(p instanceof Staff)
          {
              if(p.getID().equals(id))
              {
                Staff temp = (Staff) p;
                System.out.println();
                temp.printInfo();
                return;
              }
          }
      }
      
      System.out.println("\tNo staff matched!");
      System.out.println();   
    }
    
    public void deletePerson(Scanner s)
    {
      System.out.println();
      System.out.print("\tEnter the id of the person to delete: ");
      id = s.nextLine();
      
      for(Person p: data)
      {
        if(p.getID().equals(id))
        {
           data.remove(p);
           return;
        }
      }
      
      System.out.println("\tSorry no such person exists.");
      System.out.println();  
    }
    
    public void writeStudent(FileWriter writer, String num)
    {
    try {
      ArrayList<Student> temp = new ArrayList<Student>();
      
      writer.write("Students\n-------------------------\n");
      
       for(Person p: data)
       {
           if(p instanceof Student)
           temp.add((Student) p);
       }
       
       if(num.equals("1"))
       Collections.sort(temp, Comparator.comparing(Student::getGPA).reversed());  
       else
       Collections.sort(temp, Comparator.comparing(Student::getName));
       
       for(int i = 0; i < temp.size(); i++)
         writer.write(i+1 + ". " + temp.get(i) + "\n");   
        
    }
    catch(Exception e)
    {
        System.out.println("Could not write to file");
    }
     
    }
    
    public void writeFaculty(FileWriter writer)
    {
     try {
       ArrayList<Faculty> temp = new ArrayList<Faculty>();
       
       writer.write("Faculty Members\n-------------------------\n");
       
       for(Person p: data)
       {
           if(p instanceof Faculty)
           temp.add((Faculty) p);
       }
       
       Collections.sort(temp, Comparator.comparing(Faculty::getName));
       
       for(int i = 0; i < temp.size(); i++)
         writer.write(i+1 + ". " + temp.get(i) + "\n");  
      }
      catch(Exception e)
      {
        System.out.println("Could not write to file");
      }
       
    }
    
    public void writeStaff(FileWriter writer)
    {
      try {
      ArrayList<Staff> temp = new ArrayList<Staff>(); 
       
       writer.write("Staff Members\n-------------------------\n");
       
       for(Person p: data)
       {
           if(p instanceof Staff)
           temp.add((Staff) p);
       }
       
       Collections.sort(temp, Comparator.comparing(Staff::getName));
       
       for(int i = 0; i < temp.size(); i++)
         writer.write(i+1 + ". " + temp.get(i) + "\n");
         
       }
       catch(Exception e)
       {
           System.out.println("Could not write to file");
       }
       
    }
    
    private boolean isValidDepartment(String id)
    {
       return id.toLowerCase().equals("mathematics") || id.toLowerCase().equals("engineering") || id.toLowerCase().equals("english");
    }
    
    private boolean isValidRank(String id)
    {
        return id.toLowerCase().equals("professor") || id.toLowerCase().equals("adjunct");
    }
    
    private boolean isUniqueID(String id)
    {
        for(Person p: data)
        {
            if(p.getID().equals(id))
            return false;
        }
        
        return true;
    }
}
