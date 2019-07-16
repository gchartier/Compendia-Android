package com.gabrieldchartier.compendia.util;

import com.gabrieldchartier.compendia.models.Comic;
import com.gabrieldchartier.compendia.models.ComicBox;
import com.gabrieldchartier.compendia.models.ComicCreator;
import com.gabrieldchartier.compendia.models.ComicReview;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class TempCollection
{
    // Creators
    private static final ComicCreator newCreator1 = new ComicCreator(UUID.randomUUID(),
            "Jeff Lemire", new String[]{"Writer","Artist"});
    private static final ComicCreator newCreator2 = new ComicCreator(UUID.randomUUID(),
            "Brian K. Vaughan", new String[]{"Writer"});
    private static final ComicCreator newCreator3 = new ComicCreator(UUID.randomUUID(),
            "Alan Moore", new String[]{"Writer","Artist", "Inker", "Penciler"});
    private static final ComicCreator newCreator4 = new ComicCreator(UUID.randomUUID(),
            "Jill Stein", new String[]{"Cover Artist"});
    private static final ComicCreator newCreator5 = new ComicCreator(UUID.randomUUID(),
            "Gabriel Chartier", new String[]{"Cover Artist"});

    // Comics
    private static final Comic Thumbs1 = new Comic("5cf99e46-1fc1-313f-9471-dca900000000",
            "Thumbs #1 (Of 5)", "comic1",
            new ArrayList<ComicCreator>(){{this.add(newCreator1);}}, "Image Comics", "19284758-9282-9282-9282-102948573645", "",
            "", null, "July 13, 2019", "$4.99", "Imagine someone like, say, Mark Zuckerberg createdhis own army of tech-obsessed teens and directed them to take on the government. What would the fallout be? Charley “Thumbs” Fellows is a member of just such an army. Poor and raised by the influential MOM™ app, he finds himself in the center of a war. The Social Network meets Blade Runner in this big event book from the team that brought you the hit series THE FEW!",
            0, "", "", 4, "", "", 15, 2, 22, 22, 3.8f, 25, 12, "Thumbs", UUID.randomUUID().toString(),
            1, "", 1, true, false, false, 5, 1, 1, 0, new ArrayList<String>(){{this.add("5cf99e4d-1fc1-313f-9471-dcb200000000"); this.add("5cf99e54-1fc1-313f-9471-dcbb00000000");}}, 3.5f, "June 9th, 2019", "$4.99",
            "Titan Games And Comics", "Near Mint", "", 0, 1, "", false);
    private static final Comic Birthright36 = new Comic("5cf99e4d-1fc1-313f-9471-dcb200000000",
            "Birthright #36", "comic2",
            new ArrayList<ComicCreator>(){{this.add(newCreator2);
                this.add(newCreator1);}}, "Image Comics", "19284758-9282-9282-9282-102948573645", "",
            "", null, "July 13, 2019", "$4.99", "Imagine someone like, say, Mark Zuckerberg createdhis own army of tech-obsessed teens and directed them to take on the government. What would the fallout be? Charley “Thumbs” Fellows is a member of just such an army. Poor and raised by the influential MOM™ app, he finds himself in the center of a war. The Social Network meets Blade Runner in this big event book from the team that brought you the hit series THE FEW!",
            0, "", "", 4, "", "", 15, 2, 22, 22, 3.8f, 25, 12, "Birthright", UUID.randomUUID().toString(),
            1, "", 1, true, true, false, 5, 1, 1, 0, null, 3.5f, "June 9th, 2019", "$4.99",
            "Titan Games And Comics", "Near Mint", "", 0, 1, "", false);
    private static final Comic CemeteryBeachTP = new Comic("5cf99e54-1fc1-313f-9471-dcbb00000000",
            "Cemetery Beach Tp", "comic3",
            new ArrayList<ComicCreator>(){{this.add(newCreator1);
                this.add(newCreator2);
                this.add(newCreator3);}}, "Image Comics", "19284758-9282-9282-9282-102948573645", "",
            "", null, "July 13, 2019", "$4.99", "Imagine someone like, say, Mark Zuckerberg createdhis own army of tech-obsessed teens and directed them to take on the government. What would the fallout be? Charley “Thumbs” Fellows is a member of just such an army. Poor and raised by the influential MOM™ app, he finds himself in the center of a war. The Social Network meets Blade Runner in this big event book from the team that brought you the hit series THE FEW!",
            0, "", "", 4, "", "", 15, 2, 22, 22, 3.8f, 25, 12, "Cemetery Beach", UUID.randomUUID().toString(),
            1, "", 0, true, false, false, 5, 1, 1, 0, null, 3.5f, "June 9th, 2019", "$4.99",
            "Titan Games And Comics", "Near Mint", "", 0, 1, "", false);
    private static final Comic Criminal5 = new Comic("5cf99e5a-1fc1-313f-9471-dcc400000000",
            "Criminal #5", "comic4",
            new ArrayList<ComicCreator>(){{this.add(newCreator4);
                this.add(newCreator3);
                this.add(newCreator1);
                this.add(newCreator2);}}, "Image Comics", "19284758-9282-9282-9282-102948573645", "",
            "", null, "July 13, 2019", "$4.99", "Imagine someone like, say, Mark Zuckerberg createdhis own army of tech-obsessed teens and directed them to take on the government. What would the fallout be? Charley “Thumbs” Fellows is a member of just such an army. Poor and raised by the influential MOM™ app, he finds himself in the center of a war. The Social Network meets Blade Runner in this big event book from the team that brought you the hit series THE FEW!",
            0, "", "", 4, "", "", 15, 2, 22, 22, 3.8f, 25, 12, "Criminal", UUID.randomUUID().toString(),
            1, "", 0, true, false, false, 5, 1, 1, 0, null, 3.5f, "June 9th, 2019", "$4.99",
            "Titan Games And Comics", "Near Mint", "", 0, 1, "", false);
    private static final Comic DieTp1 = new Comic("5cf99e61-1fc1-313f-9471-dccd00000000",
            "Die Tp Vol. 01 Fantasy Heartbreaker", "comic5",
            new ArrayList<ComicCreator>(){{this.add(newCreator4);
                this.add(newCreator3);
                this.add(newCreator1);
                this.add(newCreator2);
                this.add(newCreator5);}}, "Image Comics", "19284758-9282-9282-9282-102948573645", "",
            "", null, "July 13, 2019", "$4.99", "Imagine someone like, say, Mark Zuckerberg createdhis own army of tech-obsessed teens and directed them to take on the government. What would the fallout be? Charley “Thumbs” Fellows is a member of just such an army. Poor and raised by the influential MOM™ app, he finds himself in the center of a war. The Social Network meets Blade Runner in this big event book from the team that brought you the hit series THE FEW!",
            0, "", "", 4, "", "", 15, 2, 22, 22, 3.8f, 25, 12, "Die", UUID.randomUUID().toString(),
            1, "", 1, true, false, false, 5, 1, 1, 0, null, 3.5f, "June 9th, 2019", "$4.99",
            "Titan Games And Comics", "Near Mint", "", 0, 1, "", false);
    private static final Comic Eclipse16 = new Comic("5cf99e68-1fc1-313f-9471-dcd600000000",
            "Eclipse #16", "comic6",
            new ArrayList<ComicCreator>(), "Image Comics", "19284758-9282-9282-9282-102948573645", "",
            "", null, "July 10, 2019", "$4.99", "Imagine someone like, say, Mark Zuckerberg createdhis own army of tech-obsessed teens and directed them to take on the government. What would the fallout be? Charley “Thumbs” Fellows is a member of just such an army. Poor and raised by the influential MOM™ app, he finds himself in the center of a war. The Social Network meets Blade Runner in this big event book from the team that brought you the hit series THE FEW!",
            0, "", "", 4, "", "", 15, 2, 22, 22, 3.8f, 25, 12, "Eclipse", UUID.randomUUID().toString(),
            1, "", 1, true, false, false, 5, 1, 1, 0, null, 3.5f, "June 9th, 2019", "$4.99",
            "Titan Games And Comics", "Near Mint", "", 0, 1, "", false);
    private static final Comic HackSlashOmni1 = new Comic("5cf99e6f-1fc1-313f-9471-dcdf00000000",
            "Hack Slash Omnibus Tp Vol. 06", "comic7",
            new ArrayList<ComicCreator>(), "Image Comics", "19284758-9282-9282-9282-102948573645", "",
            "", null, "July 10, 2019", "$4.99", "Imagine someone like, say, Mark Zuckerberg createdhis own army of tech-obsessed teens and directed them to take on the government. What would the fallout be? Charley “Thumbs” Fellows is a member of just such an army. Poor and raised by the influential MOM™ app, he finds himself in the center of a war. The Social Network meets Blade Runner in this big event book from the team that brought you the hit series THE FEW!",
            0, "", "", 4, "", "", 15, 2, 22, 22, 3.8f, 25, 12, "Hack Slash", UUID.randomUUID().toString(),
            1, "", 0, true, false, false, 5, 1, 1, 0, null, 3.5f, "June 9th, 2019", "$4.99",
            "Titan Games And Comics", "Near Mint", "", 0, 1, "", false);
    private static final Comic ManEaters9A = new Comic("5cf99e76-1fc1-313f-9471-dce800000000",
            "Man-Eaters #9 Cover A Miternique", "comic8",
            new ArrayList<ComicCreator>(), "Image Comics", "19284758-9282-9282-9282-102948573645", "",
            "", null, "July 10, 2019", "$4.99", "Imagine someone like, say, Mark Zuckerberg createdhis own army of tech-obsessed teens and directed them to take on the government. What would the fallout be? Charley “Thumbs” Fellows is a member of just such an army. Poor and raised by the influential MOM™ app, he finds himself in the center of a war. The Social Network meets Blade Runner in this big event book from the team that brought you the hit series THE FEW!",
            0, "", "", 4, "", "", 15, 2, 22, 22, 3.8f, 25, 12, "Man-Eaters", UUID.randomUUID().toString(),
            1, "9A", 0, true, false, false, 5, 1, 1, 0, null, 3.5f, "June 9th, 2019", "$4.99",
            "Titan Games And Comics", "Near Mint", "", 0, 1, "", true);
    private static final Comic ManEaters9B = new Comic("5cf99e7c-1fc1-313f-9471-dcef00000000",
            "Man-Eaters #9 Cover B McCall", "comic9",
            new ArrayList<ComicCreator>(), "Image Comics", "19284758-9282-9282-9282-102948573645", "",
            "", null, "July 17, 2019", "$4.99", "Imagine someone like, say, Mark Zuckerberg createdhis own army of tech-obsessed teens and directed them to take on the government. What would the fallout be? Charley “Thumbs” Fellows is a member of just such an army. Poor and raised by the influential MOM™ app, he finds himself in the center of a war. The Social Network meets Blade Runner in this big event book from the team that brought you the hit series THE FEW!",
            0, "", "", 4, "", "", 15, 2, 22, 22, 3.8f, 25, 12, "Man-Eaters", UUID.randomUUID().toString(),
            1, "9B", 1, true, false, false, 5, 1, 1, 0, null, 3.5f, "June 9th, 2019", "$4.99",
            "Titan Games And Comics", "Near Mint", "", 0, 1, "", true);
    private static final Comic ManEatersTP2 = new Comic("5cf99e83-1fc1-313f-9471-dcf800000000",
            "Man-Eaters Tp Vol. 02", "comic10",
            new ArrayList<ComicCreator>(), "Image Comics", "19284758-9282-9282-9282-102948573645", "",
            "", null, "July 17, 2019", "$4.99", "Imagine someone like, say, Mark Zuckerberg createdhis own army of tech-obsessed teens and directed them to take on the government. What would the fallout be? Charley “Thumbs” Fellows is a member of just such an army. Poor and raised by the influential MOM™ app, he finds himself in the center of a war. The Social Network meets Blade Runner in this big event book from the team that brought you the hit series THE FEW!",
            0, "", "", 4, "", "", 15, 2, 22, 22, 3.8f, 25, 12, "Man-Eaters", UUID.randomUUID().toString(),
            1, "", 1, true, false, false, 5, 1, 1, 0, null, 3.5f, "June 9th, 2019", "$4.99",
            "Titan Games And Comics", "Near Mint", "", 0, 1, "", false);
    private static final Comic MirrorTheNestTP = new Comic("5cf99e8b-1fc1-313f-9471-dd0300000000",
            "Mirror the Nest Tp", "comic11",
            new ArrayList<ComicCreator>(), "Image Comics", "19284758-9282-9282-9282-102948573645", "",
            "", null, "July 17, 2019", "$4.99", "Imagine someone like, say, Mark Zuckerberg createdhis own army of tech-obsessed teens and directed them to take on the government. What would the fallout be? Charley “Thumbs” Fellows is a member of just such an army. Poor and raised by the influential MOM™ app, he finds himself in the center of a war. The Social Network meets Blade Runner in this big event book from the team that brought you the hit series THE FEW!",
            0, "", "", 4, "", "", 15, 2, 22, 22, 3.8f, 25, 12, "Thumbs", UUID.randomUUID().toString(),
            1, "", 1, true, false, false, 5, 1, 1, 0, null, 3.5f, "June 9th, 2019", "$4.99",
            "Titan Games And Comics", "Near Mint", "", 0, 1, "", false);
    private static final Comic PaperGirls29 = new Comic("5cf99e92-1fc1-313f-9471-dd1000000000",
            "Paper Girls #29", "comic12",
            new ArrayList<ComicCreator>(), "Image Comics", "19284758-9282-9282-9282-102948573645", "",
            "", null, "July 17, 2019", "$4.99", "Imagine someone like, say, Mark Zuckerberg createdhis own army of tech-obsessed teens and directed them to take on the government. What would the fallout be? Charley “Thumbs” Fellows is a member of just such an army. Poor and raised by the influential MOM™ app, he finds himself in the center of a war. The Social Network meets Blade Runner in this big event book from the team that brought you the hit series THE FEW!",
            0, "", "", 4, "", "", 15, 2, 22, 22, 3.8f, 25, 12, "Thumbs", UUID.randomUUID().toString(),
            1, "", 1, true, false, false, 5, 1, 1, 0, null, 3.5f, "June 9th, 2019", "$4.99",
            "Titan Games And Comics", "Near Mint", "", 0, 1, "", false);
    private static final Comic SectionZero3A = new Comic("5cf99e99-1fc1-313f-9471-dd1900000000",
            "Section Zero #3cvr A Grummett & Kesel", "comic13",
            new ArrayList<ComicCreator>(), "Image Comics", "19284758-9282-9282-9282-102948573645", "",
            "", null, "July 24, 2019", "$4.99", "Imagine someone like, say, Mark Zuckerberg createdhis own army of tech-obsessed teens and directed them to take on the government. What would the fallout be? Charley “Thumbs” Fellows is a member of just such an army. Poor and raised by the influential MOM™ app, he finds himself in the center of a war. The Social Network meets Blade Runner in this big event book from the team that brought you the hit series THE FEW!",
            0, "", "", 4, "", "", 15, 2, 22, 22, 3.8f, 25, 12, "Thumbs", UUID.randomUUID().toString(),
            1, "3A", 0, true, false, false, 5, 1, 1, 0, new ArrayList<String>(){{this.add("5cf99ea0-1fc1-313f-9471-dd2600000000"); this.add("5cf99ea6-1fc1-313f-9471-dd3100000000");}}, 3.5f, "June 9th, 2019", "$4.99",
            "Titan Games And Comics", "Near Mint", "", 0, 1, "", true);
    private static final Comic SectionZero3B = new Comic("5cf99ea0-1fc1-313f-9471-dd2600000000",
            "Section Zero #3cvr B Gibbons", "comic14", new ArrayList<ComicCreator>(), "Image Comics", "19284758-9282-9282-9282-102948573645", "",
            "", null, "July 24, 2019", "$4.99", "Imagine someone like, say, Mark Zuckerberg createdhis own army of tech-obsessed teens and directed them to take on the government. What would the fallout be? Charley “Thumbs” Fellows is a member of just such an army. Poor and raised by the influential MOM™ app, he finds himself in the center of a war. The Social Network meets Blade Runner in this big event book from the team that brought you the hit series THE FEW!",
            0, "", "", 4, "", "", 15, 2, 22, 22, 3.8f, 25, 12, "Thumbs", UUID.randomUUID().toString(),
            1, "3B", 1, true, false, false, 5, 1, 1, 0, null, 3.5f, "June 9th, 2019", "$4.99",
            "Titan Games And Comics", "Near Mint", "", 0, 1, "", true);
    private static final Comic SectionZero3C = new Comic("5cf99ea6-1fc1-313f-9471-dd3100000000",
            "Section Zero #3cvr C Grummett", "comic15", new ArrayList<ComicCreator>(), "Image Comics", "19284758-9282-9282-9282-102948573645", "",
            "", null, "July 24, 2019", "$4.99", "Imagine someone like, say, Mark Zuckerberg createdhis own army of tech-obsessed teens and directed them to take on the government. What would the fallout be? Charley “Thumbs” Fellows is a member of just such an army. Poor and raised by the influential MOM™ app, he finds himself in the center of a war. The Social Network meets Blade Runner in this big event book from the team that brought you the hit series THE FEW!",
            0, "", "", 4, "", "", 15, 2, 22, 22, 3.8f, 25, 12, "Thumbs", UUID.randomUUID().toString(),
            1, "3C", 1, true, false, false, 5, 1, 1, 0, null, 3.5f, "June 9th, 2019", "$4.99",
            "Titan Games And Comics", "Near Mint", "", 0, 1, "", true);

    // Comic Boxes
    private static final List<Comic> tempRead = new ArrayList<>();
    private static final List<Comic> tempWant = new ArrayList<>();
    private static final List<Comic> tempFavorite = new ArrayList<>();

    public List<Comic> comics = new ArrayList<>();
    public List<ComicBox> comicBoxes = new ArrayList<>();
    public List<ComicReview> comicReviews = new ArrayList<>();

    public TempCollection()
    {
        comics.add(Thumbs1);
        comics.add(Birthright36);
        comics.add(CemeteryBeachTP);
        comics.add(Criminal5);
        comics.add(DieTp1);
        comics.add(Eclipse16);
        comics.add(HackSlashOmni1);
        comics.add(ManEaters9A);
        comics.add(ManEaters9B);
        comics.add(ManEatersTP2);
        comics.add(MirrorTheNestTP);
        comics.add(PaperGirls29);
        comics.add(SectionZero3A);
        comics.add(SectionZero3B);
        comics.add(SectionZero3C);

        tempRead.add(comics.get(0));
        tempRead.add(comics.get(1));
        tempRead.add(comics.get(2));
        tempRead.add(comics.get(3));

        tempWant.add(comics.get(0));
        tempWant.add(comics.get(1));
        tempWant.add(comics.get(2));
        tempWant.add(comics.get(3));

        tempFavorite.add(comics.get(0));
        tempFavorite.add(comics.get(1));
        tempFavorite.add(comics.get(2));
        tempFavorite.add(comics.get(3));

        comicBoxes.add(new ComicBox(ComicBox.READ_BOX_NAME, tempRead, Calendar.getInstance().getTime()));
        comicBoxes.add(new ComicBox(ComicBox.WANT_BOX_NAME, tempWant, Calendar.getInstance().getTime()));
        comicBoxes.add(new ComicBox(ComicBox.FAVORITE_BOX_NAME, tempFavorite, Calendar.getInstance().getTime()));

        comicReviews.add(new ComicReview(UUID.randomUUID(), comics.get(0).getID(),
                "Great read! I loved how this one wadha adkhwhd ajw hjwhwjawdhawkjd  j wdak h wjkdhkj ah!",
                "Loved it!", false, 6));
        comicReviews.add(new ComicReview(UUID.randomUUID(), comics.get(1).getID(),
                "I'm really starting to love this series. Great character development in this issue.",
                "Can't wait for more", false, 10));
        comicReviews.add(new ComicReview(UUID.randomUUID(), comics.get(0).getID(),
                "I can't believe my favorite character died in an annoying way! The writers suck.",
                "BOOOO!", true, 1));
    }
}
