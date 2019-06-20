package com.gabrieldchartier.compendia.util;

import com.gabrieldchartier.compendia.models.Comic;

import java.util.List;

public class TempCollection
{
    public List<Comic> comics;

    private static final Comic Thumbs1 = new Comic("5cf99e461fc1313f9471dca9", "Thumbs #1 (Of 5)", "https://www.previewsworld.com/SiteImage/CatalogImage/STL120775?type=1");
    private static final Comic Birthright36 = new Comic("5cf99e4d1fc1313f9471dcb2", "Birthright #36", "https://www.previewsworld.com/SiteImage/CatalogImage/STL120930?type=1");
    private static final Comic CemeteryBeachTP = new Comic("5cf99e541fc1313f9471dcbb", "Cemetery Beach Tp", "https://www.previewsworld.com/SiteImage/CatalogImage/STL107296?type=1");
    private static final Comic Criminal5 = new Comic("5cf99e5a1fc1313f9471dcc4", "Criminal #5", "https://www.previewsworld.com/SiteImage/CatalogImage/STL120835?type=1");
    private static final Comic DieTp1 = new Comic("5cf99e611fc1313f9471dccd", "Die Tp Vol. 01 Fantasy Heartbreaker", "https://www.previewsworld.com/SiteImage/CatalogImage/STL108039?type=1");
    private static final Comic Eclipse16 = new Comic("5cf99e681fc1313f9471dcd6", "Eclipse #16", "https://www.previewsworld.com/SiteImage/CatalogImage/STL120793?type=1");
    private static final Comic HackSlashOmni1 = new Comic("5cf99e6f1fc1313f9471dcdf", "Hack Slash Omnibus Tp Vol. 06", "https://www.previewsworld.com/SiteImage/CatalogImage/STL107306?type=1");
    private static final Comic ManEaters9A = new Comic("5cf99e761fc1313f9471dce8", "Man-Eaters #9 Cover A Miternique", "https://www.previewsworld.com/SiteImage/CatalogImage/STL120836?type=1");
    private static final Comic ManEaters9B = new Comic("5cf99e7c1fc1313f9471dcef", "Man-Eaters #9 Cover B McCall", "https://www.previewsworld.com/SiteImage/CatalogImage/STL120837?type=1");
    private static final Comic ManEatersTP2 = new Comic("5cf99e831fc1313f9471dcf8", "Man-Eaters Tp Vol. 02", "https://www.previewsworld.com/SiteImage/CatalogImage/STL117272?type=1");
    private static final Comic MirrorTheNestTP = new Comic("5cf99e8b1fc1313f9471dd03", "Mirror the Nest Tp", "https://www.previewsworld.com/SiteImage/CatalogImage/STL074459?type=1");
    private static final Comic PaperGirls29 = new Comic("5cf99e921fc1313f9471dd10", "Paper Girls #29", "https://www.previewsworld.com/SiteImage/CatalogImage/STL120814?type=1");
    private static final Comic SectionZero3A = new Comic("5cf99e991fc1313f9471dd19", "Section Zero #3cvr A Grummett & Kesel", "https://www.previewsworld.com/SiteImage/CatalogImage/STL120820?type=1");
    private static final Comic SectionZero3B = new Comic("5cf99ea01fc1313f9471dd26", "Section Zero #3cvr B Gibbons", "https://www.previewsworld.com/SiteImage/CatalogImage/STL120821?type=1");
    private static final Comic SectionZero3C = new Comic("5cf99ea61fc1313f9471dd31", "Section Zero #3cvr C Grummett", "https://www.previewsworld.com/SiteImage/CatalogImage/STL120822?type=1");

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
    }

    public List<Comic> getTempCollection()
    {
        return comics;
    }
}
