package it.itwtech.ateauth.enums;

public enum Permissions {
  READ("Read"), WRITE("Write"), MAINTAIN("Maintain");

  private String permission;

  private Permissions(String permission) {
    this.permission = permission;
  }

  public static Permissions getUserStatus(String permission) {
    for (Permissions permissions : values()) {
      if (permissions.permission.equals(permission)) {
        return permissions;
      }
    }
    throw new AssertionError("Permissions not found for the [Permission: " + permission + "]");
  }
}
