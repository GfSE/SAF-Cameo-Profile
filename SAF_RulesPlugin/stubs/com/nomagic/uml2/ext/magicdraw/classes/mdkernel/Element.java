package com.nomagic.uml2.ext.magicdraw.classes.mdkernel;

import com.nomagic.uml2.ext.magicdraw.mdprofiles.Stereotype;

import java.util.Collection;
import java.util.List;

public interface Element {
    String getID();
    Element getOwner();
    List<Stereotype> getAppliedStereotype();
    Collection<Diagram> get_diagramOfContext();
    Collection<Element> getOwnedElement();
}
