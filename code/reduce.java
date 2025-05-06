public class DataTree {
  public void serialize(OutputArchive oa, StringBuilder path) {
    String pathString = path.toString();
    DataNode node = getNode(pathString);
    if (node == null) {
      return;
    }
    String children[] = null;
    synchronized (node) {
      scount++;
      ContextFactory.serialize_reduced_arg0_setter(oa);
      ContextFactory.serialize_reduced_arg1_setter(node);
      oa.writeRecord(node, "node");
      Set<String> childs = node.getChildren();
      if (childs != null) {
        children = childs.toArray(new String[childs.size()]);
      }
    }
    path.append('/');
    for (String child : children) {
      path.append(child);
      serializeNode(oa, path);
    }
  }
}

public class LearnerHandler {
  public void run() {
    ...
    if (packetToSend == Leader.SNAP) {
      leader.zk.getZKDatabase().serializeSnapshot(oa);
      oa.writeString("BenWasHere", "signature");
    }
    ...
  }
}

public class LearnerHandler$Checker {
  public static void serialize_reduced(OutputArchive arg0, DataNode arg1) {
    arg0.writeRecord(arg1, "node");
  }

  public static void serialize_invoke() {
    OutputArchive arg0 = ContextFactory.serialize_reduced_arg0_getter();
    DataNode arg1 = ContextFactory.serialize_reduced_arg1_getter();
    if (arg0 != null && arg1 != null && 
        ContextFactory.serialize_reduced_arg0_version == 
        ContextFactory.serialize_reduced_arg1_version) {
      serialize_reduced(arg0, arg1);
    } else {
      LOG.debug("checker context is not ready");
    }
  }

  public static Status checkTargetFunction0() {
    try {
      serialize_invoke();
      return status.SUCCESS;
    } catch (TimeoutException e) {
      return Status.SLOW;
    } catch (ExecutionException e) {
      return Status.FAIL;
    }
  }
}


public class SyncRequestProcessor {
  public void run() {
    while (running) {
      ...
      if (logCount > (snapCount / 2 + randRoll)) {
        zks.takeSnapshot();
      }
      ...
    }
  }
}
