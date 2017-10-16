package org.w3c.utils.css.model;

import org.w3c.utils.css.enums.MediaType;
import org.w3c.utils.css.model.at.Media;
import org.w3c.utils.css.model.selectors.CssSelectorsList;

import java.util.*;

/**
 *
 * <p>Examples for MQ:<br/>
 * media screen and (max-width: 699px) and (min-width: 520px), (min-width: 1151px)<br/>
 * media screen and (min-width: 1001px)<br/>
 * media screen and (max-width: 1000px) and (min-width: 700px)<br/>
 * media screen and (max-width: 699px) and (min-width: 520px)<br/>
 * See http://www.w3schools.com/css/css3_mediaqueries_ex.asp</p>
 *
 * Created by Home on 04.11.2015.
 */
public class CssPage extends Media
{
    private Set<CssSelectorsList> selectors = new LinkedHashSet<CssSelectorsList>();
    private Collection<CssDeclarationsList> declarations = new LinkedHashSet<CssDeclarationsList>();

    public CssPage()
    {
        super();
    }

    public CssPage(String media)
    {
        super(media);
    }

    public Collection<CssSelectorsList> getSelectorsList()
    {
        return selectors;
    }

    public Collection<CssDeclarationsList> getDeclarations()
    {
        return declarations;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        if (getMediaType() != MediaType.all) builder.append(super.toString()).append(';'); //.append(" {\n");

        return builder.toString();
    }
}
