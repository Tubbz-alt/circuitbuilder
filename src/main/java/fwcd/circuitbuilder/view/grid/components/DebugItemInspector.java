package fwcd.circuitbuilder.view.grid.components;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import fwcd.circuitbuilder.model.grid.CircuitEngineModel;
import fwcd.circuitbuilder.model.grid.CircuitItemModel;
import fwcd.circuitbuilder.model.grid.CircuitItemVisitor;
import fwcd.circuitbuilder.model.grid.cable.CableModel;
import fwcd.circuitbuilder.model.grid.components.HybridComponent;
import fwcd.circuitbuilder.utils.RelativePos;
import fwcd.fructose.Option;
import fwcd.fructose.swing.Viewable;
import fwcd.fructose.text.StringUtils;

/**
 * A debug inspector for circuit items that uses
 * reflection to display the internal representation.
 */
public class DebugItemInspector implements Viewable {
	private final JPanel component;
	private final CircuitEngineModel engine;
	private final RelativePos pos;
	
	public DebugItemInspector(CircuitItemModel item, RelativePos pos, CircuitEngineModel engine) {
		this.engine = engine;
		this.pos = pos;
		
		component = new JPanel();
		component.setLayout(new BoxLayout(component, BoxLayout.Y_AXIS));
		
		appendObjectInfo(item);
		
		item.accept(new HybridMatcher())
			.stream()
			.flatMap(it -> it.getDelegates().stream())
			.forEach(this::appendObjectInfo);
	}
	
	private static class HybridMatcher implements CircuitItemVisitor<Option<HybridComponent>> {
		@Override
		public Option<HybridComponent> visitItem(CircuitItemModel item) { return Option.empty(); }
		
		@Override
		public Option<HybridComponent> visitHybrid(HybridComponent hybrid) { return Option.of(hybrid); }
	}
	
	private void appendObjectInfo(Object obj) {
		Class<?> objClass = obj.getClass();
		
		JLabel label = new JLabel(objClass.getSimpleName());
		label.setFont(label.getFont().deriveFont(18F));
		component.add(label);
		
		component.add(new JLabel("hashCode: " + Integer.toHexString(obj.hashCode())));
		
		if (objClass.equals(CableModel.class)) {
			component.add(new JLabel("Cable Networks: " + engine.getCableNetworks().stream()
				.filter(net -> net.getPositions().contains(pos))
				.map(net -> {
					CableModel other = net.cableAt(pos);
					if (other.equals(obj)) {
						return net.toString();
					} else {
						return net.toString() + " (not matching item: " + Integer.toHexString(other.hashCode()) + ")";
					}
				})
				.collect(Collectors.toList())));
		}
		
		component.add(new JSeparator(SwingConstants.HORIZONTAL));
		
		Class<?> currentClass = objClass;
		
		while (currentClass != null) {
			for (Field field : currentClass.getDeclaredFields()) {
				field.setAccessible(true);
				
				String text;
				try {
					Object value = field.get(obj);
					text = field.getName() + ":  " + StringUtils.toString(value) + " (@" + Integer.toHexString(value.hashCode()) + ")";
				} catch (ReflectiveOperationException e) {
					text = "Error: " + e.getMessage();
				}
				
				component.add(new JLabel(text));
			}
			
			currentClass = currentClass.getSuperclass();
		}
		
		component.add(new JSeparator(SwingConstants.HORIZONTAL));
	}
	
	@Override
	public JComponent getComponent() { return component; }
}
