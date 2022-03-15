package FinalAssessment;

public class LazyAndEagerLoadingQuestion1 {
    /*
    * lAZY LOADING:
    * This method sets objects to null. Object data is loaded only after and whenever invoking them,check if null,
      and if so, load object data.
    *Use Lazy Loading when you are using one-to-many collections.
    *Use Lazy Loading when you are sure that you are not using related entities instantly.
    *Initial load time much smaller than Eager loading.
    *Eager Loading:
    * eager loading initializes or loads a resource as soon as the code is executed. Eager loading also involves
       preloading related entities referenced by a resource.
    * Use Eager Loading when the relations are not too much. Thus, Eager Loading is a good practice to reduce further
       queries on the Host.
    * Use Eager Loading when you are sure that you will be using related entities with the main entity everywhere.
    *Loading too much unnecessary data might impact performance.
     */
}
