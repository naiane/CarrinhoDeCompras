package br.com.javamagazine.jee6.loja.web;

import java.util.*;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@ApplicationScoped
public class UtilsMB {
  private ResourceBundle bundle;

  @PostConstruct
  public void init() {
    this.bundle = ResourceBundle.getBundle("messages", FacesContext
        .getCurrentInstance().getViewRoot().getLocale());
  }

  public String getMessage(String chave) {
    String message = null;
    try {
      message = bundle.getString(chave);
    }
    catch (MissingResourceException e) {
      return "?? chave " + chave + " inexistente ??";
    }
    return message;
  }
}