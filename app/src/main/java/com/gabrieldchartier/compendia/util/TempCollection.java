package com.gabrieldchartier.compendia.util;

import com.gabrieldchartier.compendia.models.Comic;

import java.util.ArrayList;
import java.util.List;

public class TempCollection
{
    public List<Comic> comics;

    private static final Comic Thumbs1 = new Comic("5cf99e46-1fc1-313f-9471-dca900000000", "Thumbs #1 (Of 5)", "https://www.previewsworld.com/SiteImage/CatalogImage/STL120775?type=1");
    private static final Comic Birthright36 = new Comic("5cf99e4d-1fc1-313f-9471-dcb200000000", "Birthright #36", "https://www.previewsworld.com/SiteImage/CatalogImage/STL120930?type=1");
    private static final Comic CemeteryBeachTP = new Comic("5cf99e54-1fc1-313f-9471-dcbb00000000", "Cemetery Beach Tp", "https://www.previewsworld.com/SiteImage/CatalogImage/STL107296?type=1");
    private static final Comic Criminal5 = new Comic("5cf99e5a-1fc1-313f-9471-dcc400000000", "Criminal #5", "https://www.previewsworld.com/SiteImage/CatalogImage/STL120835?type=1");
    private static final Comic DieTp1 = new Comic("5cf99e61-1fc1-313f-9471-dccd00000000", "Die Tp Vol. 01 Fantasy Heartbreaker", "https://www.previewsworld.com/SiteImage/CatalogImage/STL108039?type=1");
    private static final Comic Eclipse16 = new Comic("5cf99e68-1fc1-313f-9471-dcd600000000", "Eclipse #16", "https://www.previewsworld.com/SiteImage/CatalogImage/STL120793?type=1");
    private static final Comic HackSlashOmni1 = new Comic("5cf99e6f-1fc1-313f-9471-dcdf00000000", "Hack Slash Omnibus Tp Vol. 06", "https://www.previewsworld.com/SiteImage/CatalogImage/STL107306?type=1");
    private static final Comic ManEaters9A = new Comic("5cf99e76-1fc1-313f-9471-dce800000000", "Man-Eaters #9 Cover A Miternique", "https://www.previewsworld.com/SiteImage/CatalogImage/STL120836?type=1");
    private static final Comic ManEaters9B = new Comic("5cf99e7c-1fc1-313f-9471-dcef00000000", "Man-Eaters #9 Cover B McCall", "https://www.previewsworld.com/SiteImage/CatalogImage/STL120837?type=1");
    private static final Comic ManEatersTP2 = new Comic("5cf99e83-1fc1-313f-9471-dcf800000000", "Man-Eaters Tp Vol. 02", "https://www.previewsworld.com/SiteImage/CatalogImage/STL117272?type=1");
    private static final Comic MirrorTheNestTP = new Comic("5cf99e8b-1fc1-313f-9471-dd0300000000", "Mirror the Nest Tp", "https://www.previewsworld.com/SiteImage/CatalogImage/STL074459?type=1");
    private static final Comic PaperGirls29 = new Comic("5cf99e92-1fc1-313f-9471-dd1000000000", "Paper Girls #29", "https://www.previewsworld.com/SiteImage/CatalogImage/STL120814?type=1");
    private static final Comic SectionZero3A = new Comic("5cf99e99-1fc1-313f-9471-dd1900000000", "Section Zero #3cvr A Grummett & Kesel", "https://www.previewsworld.com/SiteImage/CatalogImage/STL120820?type=1");
    private static final Comic SectionZero3B = new Comic("5cf99ea0-1fc1-313f-9471-dd2600000000", "Section Zero #3cvr B Gibbons", "https://www.previewsworld.com/SiteImage/CatalogImage/STL120821?type=1");
    private static final Comic SectionZero3C = new Comic("5cf99ea6-1fc1-313f-9471-dd3100000000", "Section Zero #3cvr C Grummett", "https://www.previewsworld.com/SiteImage/CatalogImage/STL120822?type=1");

    public TempCollection()
    {
        comics = new ArrayList<>();
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
    }

    public List<Comic> getTempCollection()
    {
        return comics;
    }
}
