void serializeNode(OutputArchive oa, StringBuilder path) throws IOException {
  String pathString = path.toString();
  DataNode node = getNode(pathString);
  if (node == null) {
    return;
  }
  String children[] = null;
  synchronized (node) {
    scount++;
    oa.writeString(pathString, "path");
    oa.writeRecord(node, "node");
    Set<String> childs = node.getChildren();
    if (childs != null) {
      children = childs.toArray(new String[childs.size()]);
    }
  }
  path.append('/');
  int off = path.length();
  if (children != null) {
    for (String child : children) {
      // since this is single buffer being resused
      // we need
      // to truncate the previous bytes of string.
      path.delete(off, Integer.MAX_VALUE);
      path.append(child);
      serializeNode(oa, path); // recursive serialize nodes
    }
  }
}

public void serialize(OutputArchive oa, String tag) throws IOException {
	scount = 0;
	serializeList(longKeyMap, oa);
	serializeNode(oa, new StringBuilder(""));
	// / marks end of stream
	// we need to check if clear had been called in between the snapshot.
	if (root != null) {
		oa.writeString("/", "path");
	}
}

public static void serializeSnapshot(DataTree dt,OutputArchive oa,
    Map<Long, Integer> sessions) throws IOException {
  HashMap<Long, Integer> sessSnap = new HashMap<>(sessions);
  oa.writeInt(sessSnap.size(), "count");
  for (Entry<Long, Integer> entry : sessSnap.entrySet()) {
    oa.writeLong(entry.getKey().longValue(), "id");
    oa.writeInt(entry.getValue().intValue(), "timeout");
  }
  dt.serialize(oa, "tree");
}

public void run() {
  ...
  if (packetToSend == Leader.SNAP) {
    leader.zk.getZKDatabase().serializeSnapshot(oa);
    oa.writeString("BenWasHere", "signature");
  }
  ...
}
