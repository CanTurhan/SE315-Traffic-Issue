package traficsim.monitorobj;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SynchronizedList {
	private List list;

	public SynchronizedList(List l) {
		this.list = l;
	}

	@SuppressWarnings("unchecked")
	public void addItem(Object o) {
		synchronized (this) {
			this.list.add(o);
		}
	}
	
	public Object getItem(int idx) {
		synchronized (this) {
			return this.list.get(idx);
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized List<Object> getList() {
		return this.list;
	}

	@SuppressWarnings("unchecked")
	public List remove(int numberOfElements) {
		synchronized (this) {
			final List removedCars = new ArrayList();

			for (int ii = 0; (ii < numberOfElements) && (this.size() > 0); ii++) {
				// Randomly pick an element to delete
				final int elementToRemove = (int) (Math.random() * this.size());

				// Added the deleted item to the returned list
				removedCars.add(this.list.remove(elementToRemove));
			}
			return removedCars;
		}

	}

	public void removeItem(Object o) {
		synchronized (this) {
			this.list.remove(o);
		}
	}

	public void setValues(String fieldName, Object val) {
		synchronized (this) {
			if (this.list.size() > 0) {
				final Class c = this.list.get(0).getClass();

				Field f = null;
				try {
					f = c.getDeclaredField(fieldName);
				} catch (final SecurityException e1) {
					e1.getMessage();
				} catch (final NoSuchFieldException e1) {
					e1.getMessage();
				}
				f.setAccessible(true);
				for (int i = 0; i < this.list.size(); i++) {
					try {
						f.set(this.list.get(i), val);
					} catch (final SecurityException e) {
						e.getMessage();
					} catch (final IllegalArgumentException e) {
						e.getMessage();
					} catch (final IllegalAccessException e) {
						e.getMessage();
					}
				}
			}
		}
	}

	public int size() {
		synchronized (this) {
			return this.list.size();
		}
	}
}