package io.github.mklew.geda.autodiscovery.domain;

/**
 * @author Marek Lewandowski
 * @since 7/27/13
 */
public interface Person
{
    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);
}
