package azzy.fabric.lff.structures;

import java.util.concurrent.atomic.AtomicBoolean;

abstract class PrimitiveNode<T extends NodeGraph<V, ?>, V extends PrimitiveNode<T, V>> {

    private T network;
    private AtomicBoolean scheduledUpdate;

    PrimitiveNode(T network){
        this.network = network;
    }

    public T getNetwork(){
        return network;
    }

    public void setNetwork(T network) {
        this.network = network;
    }

    @SuppressWarnings("unchecked")
    public void createEdge(V targetNode){
        network.createEdge((V) this, targetNode);
    }

    @SuppressWarnings("unchecked")
    public synchronized void createAndValidateEdge(V targetNode){
        network.createEdge((V) this, targetNode);
        network.getEdge(targetNode).validate();
    }

    public boolean hasScheduledUpdate(){
        return scheduledUpdate.get();
    }

    public abstract void update();
}
