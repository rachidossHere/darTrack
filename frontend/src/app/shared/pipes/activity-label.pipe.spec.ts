import { ActivityLabelPipe } from './activity-label.pipe';
import { DateTimePipe } from './date-time.pipe';

describe('ActivityLabelPipe', () => {
  it('should map stage update event to readable french label', () => {
    const pipe = new ActivityLabelPipe();

    expect(pipe.transform('STAGE_UPDATED')).toBe('Étape mise à jour');
    expect(pipe.transform('STAGE_APPROVED')).toBe('Étape validée');
  });
});

describe('DateTimePipe', () => {
  it('should format ISO date without seconds', () => {
    const pipe = new DateTimePipe();

    expect(pipe.transform('2021-06-21T18:50:00Z')).toBe('21/06/2021 18:50');
  });
});
