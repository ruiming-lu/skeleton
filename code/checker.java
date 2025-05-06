public class SyncRequestProcessor$Checker {
  public static void serializeSnapshot_reduced (OutputArchive arg0, DataNode arg1) {
    arg0.writeRecord(arg1, "node");
  }
  public static void serializeSnapshot_invoke() {
    Context ctx = ContextFactory.serializeSnapshot_reduced_context();
    if (ctx.status == READY) {
      OutputArchive arg0 = ctx.args_getter(0);
      DataNode arg1 = ctx.args_getter(1);
      serializeSnapshot_reduced(arg0, arg1);
    }
    else {
      LOG.debug("checker context not ready");
    }
  }
  public static Status checkTargetFunction0() {
    ...
    serializeSnapshot_invoke();
    ...
  }
}
