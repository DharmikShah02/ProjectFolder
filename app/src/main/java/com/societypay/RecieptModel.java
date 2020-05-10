package com.societypay;

public class RecieptModel
{
    private String MobileNo;

    private String Amount;

    private String Flat_no;

    private String Name;

    public String getMobileNo ()
    {
        return MobileNo;
    }

    public void setMobileNo (String MobileNo)
    {
        this.MobileNo = MobileNo;
    }

    public String getAmount ()
    {
        return Amount;
    }

    public void setAmount (String Amount)
    {
        this.Amount = Amount;
    }

    public String getFlat_no ()
    {
        return Flat_no;
    }

    public void setFlat_no (String Flat_no)
    {
        this.Flat_no = Flat_no;
    }

    public String getName ()
    {
        return Name;
    }

    public void setName (String Name)
    {
        this.Name = Name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [MobileNo = "+MobileNo+", Amount = "+Amount+", Flat_no = "+Flat_no+", Name = "+Name+"]";
    }
}