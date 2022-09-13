package UML;

/**
 * UML class
 *
 * @author Dylan Franklin
 */
public class Contacts {
    private String contactName;
    // private int contactID;
    // private String contactEmail;

    /**
     * Constructor for Contacts
     *
     * @param contactName
     */
    public Contacts(String contactName/* , int contactID , String contactEmail */) {

        this.contactName = contactName;
        // this.contactID = contactID;
        // this.contactEmail = contactEmail;
    }

    //CONTACT NAME
    public String getContactName() {
        return contactName;
    }
    public void setContactName() {
        this.contactName = contactName;
    }

    /*
    //CONTACT ID
    public int getContactID() {
         return contactID;
    }
    public void setContactID() {
         this.countryID = contactID;
    }

    //CONTACT EMAIL
    public String getContactEmail() {
         return contactEmail;
   }
   public void setContactEmail() {
         this.contactEmail =  contactEmail;
   }

  */


    @Override
    public String toString() {
        return contactName;
    }

}

