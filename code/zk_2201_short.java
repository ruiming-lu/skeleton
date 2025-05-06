void serializeNode(OutputArchive oa, String path) {
    DataNode node = getNode(path);
    synchronized (node) {
        oa.writeString(path, "path");
        oa.writeRecord(node, "node");
    }
}

int serializeNode_checker() {
    if(!isTriggered("serializeNode()")) 
        return STATUS_SKIP;
    long start = System.currentTimeMillis();
    try{
        OutputArchive oa = getContext("serializeNode()","oa");
        String path = getContext("serializeNode()","path");
        oa.writeString(path, "path");

        oa = getContext("serializeNode()","oa");
        DataNode node = getContext("serializeNode()","node");
        oa.writeRecord(node, "node");
    } catch (Exception ex)
    {
        markFail("serializeNode()","oa");
        return STATUS_FAIL;
    }
    if (System.currentTimeMillis() - start > TIMEOUT)
       return STATUS_FAIL;
    return STATUS_NORMAL;
}
